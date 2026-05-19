package roomescape.repository;

import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.member.Member;

@Repository
public class MemberRepository {
    private static final org.springframework.jdbc.core.RowMapper<Member> MEMBER_ROW_MAPPER = (resultSet, rowNum) -> Member.load(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("login_id"),
            resultSet.getString("password")
    );
    private static final String SELECT_BY_LOGIN_ID_AND_PASSWORD = "SELECT * FROM MEMBER WHERE login_id = ? AND password = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM MEMBER WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member save(Member member) {
        Map<String, String> params = Map.of(
                "name", member.getName(),
                "login_id", member.getLoginId(),
                "password", member.getPassword()
        );
        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.load(generatedKey, member.getName(), member.getLoginId(), member.getPassword());
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
