package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.request.LoginRequest;
import roomescape.controller.dto.request.RegisterRequest;
import roomescape.domain.member.Member;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeApplicationTest {

    private static final String AVAILABLE_DATE = "2099-06-01";
    private static final Member MEMBER = Member.load(1L, "zeze", "zezeId", "password");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.update("insert into reservation_time(start_at) values ('10:00')");
        jdbcTemplate.update(
                "insert into theme(name, description, thumbnail_url) values ('공포', '무서워요', 'https://zeze.com')");
        jdbcTemplate.update(
                "insert into theme(name, description, thumbnail_url) values ('개그', '재밌어요', 'https://zeze.com')");
    }

    private int availableCount(String date, long themeId) {
        return RestAssured.given()
                .when().get("/times/available?date=" + date + "&themeId=" + themeId)
                .then().statusCode(200)
                .extract().jsonPath().getList(".").size();
    }

    private void reserve(Member member, String date, Long timeId, Long themeId, String sessionId,
                         int expectedStatusCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", member.getId());
        params.put("date", date);
        params.put("timeId", timeId);
        params.put("themeId", themeId);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .sessionId(sessionId)
                .body(params)
                .when().post("/reservations")
                .then().statusCode(expectedStatusCode);
    }

    @Test
    void 예약을_조회한다() {
        String sessionId = registAndLogin();
        RestAssured.given().log().all()
                .sessionId(sessionId)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 이름으로_조회시_정상적으로_반환한다() {
        String sessionId = registAndLogin();
        reserve(MEMBER, "2099-05-01", 1L, 1L, sessionId, 201);
        reserve(MEMBER, "2099-05-02", 1L, 2L, sessionId, 201);
        reserve(MEMBER, "2099-05-03", 1L, 2L, sessionId, 201);

        RestAssured.given().params("name", "zeze")
                .sessionId(sessionId)
                .when().get("/reservations")
                .then().log().all()
                .body("size()", is(3));
    }


    @Test
    void 과거_예약_생성시_422를_반환한다() {
        String sessionId = registAndLogin();
        reserve(MEMBER, "2020-01-01", 1L, 1L, sessionId, 422);
    }

    @Test
    void 중복_예약_수행시_409를_반환한다() {
        String sessionId = registAndLogin();
        reserve(MEMBER, "2099-05-14", 1L, 1L, sessionId, 201);
        reserve(MEMBER, "2099-05-14", 1L, 1L, sessionId, 409);
    }

    @Test
    void 존재하지_않는_예약_조회시_404를_반환한다() {
        String sessionId = registAndLogin();
        RestAssured.given()
                .sessionId(sessionId)
                .when().get("/reservations/999")
                .then().statusCode(404);

    }

    @Test
    void 예약_생성_후_사용_시간_조회시_해당_시간이_제외된다() {
        String sessionId = registAndLogin();
        int before = availableCount(AVAILABLE_DATE, 1);

        reserve(MEMBER, AVAILABLE_DATE, 1L, 1L, sessionId, 201);

        int after = availableCount(AVAILABLE_DATE, 1);
        assertThat(after).isEqualTo(before - 1);
    }

    @Test
    void 예약_없는_날짜_조회시_전체_시간이_반환된다() {
        int total = RestAssured.given()
                .when().get("/times")
                .then().statusCode(200)
                .extract().jsonPath().getList(".").size();

        int available = availableCount(AVAILABLE_DATE, 1);

        assertThat(available).isEqualTo(total);
    }

    @Test
    void 다른_테마_예약은_사용_시간_조회에_영향을_주지_않는다() {
        String sessionId = registAndLogin();
        int before = availableCount(AVAILABLE_DATE, 1);

        reserve(MEMBER, AVAILABLE_DATE, 1L, 2L, sessionId, 201);

        int after = availableCount(AVAILABLE_DATE, 1);
        assertThat(after).isEqualTo(before);
    }

    @Test
    void 과거_날짜로_조회시_422를_반환한다() {
        RestAssured.given()
                .when().get("/times/available?date=2020-01-01&themeId=1")
                .then().statusCode(422);
    }

    @Test
    void themeId_없이_조회시_400을_반환한다() {
        RestAssured.given()
                .when().get("/times/available?date=" + AVAILABLE_DATE)
                .then().statusCode(400);
    }

    @Test
    void date_없이_조회시_400을_반환한다() {
        RestAssured.given()
                .when().get("/times/available?themeId=1")
                .then().statusCode(400);

    }

    @Test
    void 예약이_존재하는_시간을_지우면_409를_반환한다() {
        String sessionId = registAndLogin();
        reserve(MEMBER, "2099-05-14", 1L, 1L, sessionId, 201);

        RestAssured.given().log().all()
                .sessionId(sessionId)
                .when().delete("/admin/times/1")
                .then().log().all().statusCode(409);
    }

    @Test
    void 존재하지_않는_테마_조회시_404를_반환한다_인증X() {
        RestAssured.given()
                .when().get("/themes/999")
                .then().statusCode(404);
    }

    @Test
    void 세션_ID가_없는_요청은_401을_반환한다() {
        RestAssured.given()
                .when().delete("/admin/themes/1")
                .then().statusCode(401);
    }

    @Test
    void 잘못된_세션_ID가_입력되면_401을_반환한다() {
        RestAssured.given()
                .sessionId("RANDOM")
                .when().delete("/admin/themes/1")
                .then().statusCode(401);
    }

    @Test
    void 인증된_요청은_정상_동작한다() {
        String sessionId = registAndLogin();

        RestAssured.given()
                .sessionId(sessionId)
                .when().delete("/admin/themes/1")
                .then().statusCode(200);
    }

    private static String registAndLogin() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("zeze", "zezeId", "password"))
                .when().post("/register")
                .then().statusCode(200);

        String sessionId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("zezeId", "password"))
                .when().post("/login")
                .then().statusCode(200)
                .extract().sessionId();
        return sessionId;
    }
}
