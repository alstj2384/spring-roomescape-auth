package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.member.Member;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeName;
import roomescape.domain.theme.ThumbnailUrl;

@Repository
public class ReservationRepository {
    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> Reservation.load(
            resultSet.getLong("reservation_id"),
            Member.load(resultSet.getLong("member_id"), resultSet.getString("member_name"),
                    resultSet.getString("login_id"), resultSet.getString("password")),
            new ReservationDate(resultSet.getDate("date").toLocalDate()),
            ReservationTime.of(resultSet.getLong("time_id"), resultSet.getTime("start_at").toLocalTime()),
            Theme.load(resultSet.getLong("theme_id"), new ThemeName(resultSet.getString("theme_name")),
                    resultSet.getString("description"), new ThumbnailUrl(resultSet.getString("thumbnail_url"))));
    private static final String SELECT_ALL = """
            SELECT r.id   AS reservation_id,
                   r.date,
                   rt.id  AS time_id,
                   rt.start_at,
                   t.id   AS theme_id,
                   t.name AS theme_name,
                   t.description,
                   t.thumbnail_url,
                   m.id AS member_id,
                   m.name AS member_name,
                   m.login_id,
                   m.password
            FROM reservation r
            INNER JOIN reservation_time rt ON r.time_id  = rt.id
            INNER JOIN theme             t  ON r.theme_id = t.id
            INNER JOIN member m ON r.member_id = m.id
            """;
    private static final String UPDATE = """
            UPDATE reservation
                SET
                    member_id = ?,
                    date = ?,
                    time_id = ?,
                    theme_id = ?
            WHERE id = ?
            """;
    private static final String SELECT_BY_ID = SELECT_ALL + "WHERE r.id = ?";
    private static final String SELECT_BY_MEMBER_ID = SELECT_ALL + "WHERE m.id = ?";
    private static final String SELECT_BY_MEMBER_NAME = SELECT_ALL + "WHERE m.name = ?";
    private static final String EXISTS_BY_DATE_AND_TIME_AND_THEME_ID = """
            SELECT EXISTS (
                SELECT 1
                FROM reservation
                WHERE date = ? AND time_id = ? AND theme_id = ?
            )
            """;
    private static final String EXISTS_BY_TIME_ID = """
            SELECT EXISTS (
                SELECT 1 
                    FROM reservation
                    WHERE time_id = ?
                    )
            """;
    private static final String EXISTS_BY_THEME_ID = """
            SELECT EXISTS (
                SELECT 1 
                    FROM reservation
                    WHERE theme_id = ?
                    )
            """;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_ALL, RESERVATION_ROW_MAPPER);
    }

    public List<Reservation> findAllByName(String reservationName) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_NAME, RESERVATION_ROW_MAPPER, reservationName);
    }

    public Optional<Reservation> findById(long reservationId) {
        List<Reservation> result = jdbcTemplate.query(SELECT_BY_ID, RESERVATION_ROW_MAPPER, reservationId);
        return result.stream().findFirst();
    }

    public List<Reservation> findAllByMemberId(long memberId) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_ID, RESERVATION_ROW_MAPPER, memberId);
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> params = Map.of(
                "member_id", reservation.getMember().getId(),
                "date", reservation.getDate().getDate(),
                "time_id", reservation.getTime().getId(),
                "theme_id", reservation.getTheme().getId()
        );

        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Reservation.load(generatedKey, reservation.getMember(), reservation.getDate(),
                reservation.getTime(),
                reservation.getTheme());
    }

    public Reservation update(long id, Reservation target) {
        jdbcTemplate.update(UPDATE, target.getMember().getId(), target.getDate().getDate(), target.getTime().getId(),
                target.getTheme().getId(), id);

        return Reservation.load(id, target.getMember(), target.getDate(), target.getTime(), target.getTheme());
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByTimeId(long reservationTimeId) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_BY_TIME_ID, Boolean.class, reservationTimeId));
    }

    public boolean existsByThemeId(long themeId) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_BY_THEME_ID, Boolean.class, themeId));
    }

    public boolean existsByTimeAndThemeAndDate(Long timeId, Long themeId, LocalDate date) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_BY_DATE_AND_TIME_AND_THEME_ID, Boolean.class, date, timeId,
                        themeId));
    }
}
