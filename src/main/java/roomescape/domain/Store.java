package roomescape.domain;

import roomescape.domain.member.Member;

public class Store {
    private final long id;
    private final String name;
    private final Member member;

    public Store(long id, String name, Member member) {
        this.id = id;
        this.name = name;
        this.member = member;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Member getMember() {
        return member;
    }
}
