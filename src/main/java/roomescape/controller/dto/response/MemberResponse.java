package roomescape.controller.dto.response;

import roomescape.domain.member.Member;
import roomescape.domain.member.Role;

public class MemberResponse {
    private final long id;
    private final String name;
    private final Role role;

    public MemberResponse(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public static MemberResponse toDto(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getRole());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
