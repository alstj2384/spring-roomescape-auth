package roomescape.domain.member;

public class Member {
    private final long id;
    private final String name;
    private final String loginId;
    private final String password;
    private final Role role;

    private Member(long id, String name, String loginId, String password, Role role) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    public static Member load(long id, String name, String loginId, String password, Role role) {
        return new Member(id, name, loginId, password, role);
    }

    public static Member create(String name, String loginId, String password, Role role) {
        return new Member(0L, name, loginId, password, role);
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

    public Role getRole() {
        return role;
    }
}
