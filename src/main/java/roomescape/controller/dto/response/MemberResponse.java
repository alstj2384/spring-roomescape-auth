package roomescape.controller.dto.response;

import roomescape.domain.member.Member;

public class MemberResponse {
    private final long id;
    private final String name;

    public MemberResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberResponse toDto(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
