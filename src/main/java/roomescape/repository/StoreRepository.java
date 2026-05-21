package roomescape.repository;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Store;
import roomescape.domain.member.Member;
import roomescape.domain.member.Role;

@Repository
public class StoreRepository {
    private static final RowMapper<Store> STORE_ROW_MAPPER = (resultSet, rowNum) -> new Store(
            resultSet.getLong("store_id"),
            resultSet.getString("store_name"),
            Member.load(resultSet.getLong("member_id"), resultSet.getString("member_name"),
                    resultSet.getString("member_login_id"), resultSet.getString("member_password"),
                    Role.valueOf(resultSet.getString("member_role")))
    );
    private static final String SELECT_BY_MEMBER_ID =
            "SELECT s.id as store_id, s.name as store_name, s.member_id, m.name as member_name, m.login_id as member_login_id, m.password as member_password, m.role as member_role"
                    + " FROM store s INNER JOIN member m ON m.id = s.member_id WHERE member_id = ?";

    private static final String SELECT_BY_ID =
            "SELECT s.id as store_id, s.name as store_name, s.member_id, m.name as member_name, m.login_id as member_login_id, m.password as member_password, m.role as member_role"
                    + " FROM store s INNER JOIN member m ON m.id = s.member_id WHERE s.id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public StoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("store")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Store> findByMemberId(long memberId) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_ID, STORE_ROW_MAPPER, memberId).stream().findAny();
    }

    public Optional<Store> findById(long id) {
        return jdbcTemplate.query(SELECT_BY_ID, STORE_ROW_MAPPER, id).stream().findAny();
    }
}
