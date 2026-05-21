package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;

@Repository
public class ReservationRepository {
    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> Reservation.load(
            rs.getLong("reservation_id"),
            ReservationRowMappers.mapMember(rs),
            new ReservationDate(rs.getDate("date").toLocalDate()),
            ReservationRowMappers.mapTime(rs),
            ReservationRowMappers.mapTheme(rs),
            ReservationRowMappers.mapStore(rs));
    private static final String SELECT_ALL = """
            SELECT r.id   AS reservation_id,
                   r.date,
                   rt.id  AS time_time_id,
                   rt.start_at AS time_start_at,
                   t.id   AS theme_theme_id,
                   t.name AS theme_theme_name,
                   t.description AS theme_description,
                   t.thumbnail_url AS theme_thumbnail_url,
                   m.id AS member_id,
                   m.name AS member_name,
                   m.login_id AS member_login_id,
                   m.password AS member_password,
                   m.role AS member_role,
                   s.id AS theme_store_id,
                   s.name AS theme_store_name,
                   sm.id AS store_member_id,
                   sm.name AS store_member_name,
                   sm.login_id AS store_member_login_id,
                   sm.password AS store_member_password,
                   sm.role AS store_member_role
            FROM reservation r
            INNER JOIN reservation_time rt ON r.time_id  = rt.id
            INNER JOIN theme             t  ON r.theme_id = t.id
            INNER JOIN member m ON r.member_id = m.id
            INNER JOIN store s ON r.store_id = s.id
            INNER JOIN member sm ON s.member_id = sm.id
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
    private static final String EXISTS_BY_DATE_AND_TIME_AND_THEME_ID_EXCLUDE_ID = """
            SELECT EXISTS (
                SELECT 1
                FROM reservation
                WHERE date = ? AND time_id = ? AND theme_id = ? AND id <> ?
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
                "theme_id", reservation.getTheme().getId(),
                "store_id", reservation.getStore().getId()
        );

        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Reservation.load(generatedKey, reservation.getMember(), reservation.getDate(), reservation.getTime(),
                reservation.getTheme(), reservation.getStore());
    }

    public Reservation update(long id, Reservation target) {
        jdbcTemplate.update(UPDATE, target.getMember().getId(), target.getDate().getDate(), target.getTime().getId(),
                target.getTheme().getId(), id);

        return Reservation.load(id, target.getMember(), target.getDate(), target.getTime(), target.getTheme(),
                target.getStore());
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

    public boolean existsByTimeAndThemeAndDateExcludeId(Long timeId, Long themeId, LocalDate date, long id) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_BY_DATE_AND_TIME_AND_THEME_ID_EXCLUDE_ID, Boolean.class, date,
                        timeId,
                        themeId, id));
    }
}
