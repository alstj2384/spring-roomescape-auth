package roomescape.domain.member;

public class Member {
    private final long id;
    private final String name;
    private final String loginId;
    private final String password;

    public Member(long id, String name, String loginId, String password) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
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
