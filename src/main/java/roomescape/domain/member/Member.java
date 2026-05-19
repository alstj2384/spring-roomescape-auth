package roomescape.domain.member;

import java.util.Objects;

public class Member {
    private final long id;
    private final String name;
    private final String loginId;
    private final String password;

    private Member(long id, String name, String loginId, String password) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.loginId = Objects.requireNonNull(loginId);
        this.password = Objects.requireNonNull(password);
    }

    public static Member load(long id, String name, String loginId, String password) {
        return new Member(id, name, loginId, password);
    }

    public static Member create(String name, String loginId, String password) {
        return new Member(0L, name, loginId, password);
    }

    public Member(String name, String loginId, String password) {
        this.id = 0L;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
