package roomescape.repository;

import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.member.Member;

@Repository
public class MemberRepository {
    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (resultSet, rowNum) -> ReservationRowMappers.mapMember(
            resultSet);
    private static final String SELECT_BY_LOGIN_ID_AND_PASSWORD = "SELECT id AS member_id, name AS member_name, login_id AS member_login_id, password AS member_password, role AS member_role FROM MEMBER WHERE login_id = ? AND password = ?";
    private static final String SELECT_BY_ID = "SELECT id AS member_id, name AS member_name, login_id AS member_login_id, password AS member_password, role AS member_role FROM MEMBER WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member save(Member member) {
        Map<String, Object> params = Map.of(
                "name", member.getName(),
                "login_id", member.getLoginId(),
                "password", member.getPassword(),
                "role", member.getRole().name()
        );
        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.load(generatedKey, member.getName(), member.getLoginId(), member.getPassword(), member.getRole());
    }

    public Optional<Member> findByLoginIdAndPassword(String memberId, String password) {
        return jdbcTemplate.query(SELECT_BY_LOGIN_ID_AND_PASSWORD, MEMBER_ROW_MAPPER, memberId, password).stream()
                .findAny();
    }

    public Optional<Member> findById(long memberId) {
        return jdbcTemplate.query(SELECT_BY_ID, MEMBER_ROW_MAPPER, memberId).stream()
                .findAny();
    }
}
