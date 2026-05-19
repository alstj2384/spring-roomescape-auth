package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.domain.member.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private final Map<Long, Member> store;
    private long index = 0L;

    public MemoryMemberRepository() {
        this.store = new HashMap<>();
    }

    public Member save(Member member){
        Member created = new Member(index, member.getName(), member.getLoginId(), member.getPassword());
        store.put(index++, created);
        return created;
    }

    public Optional<Member> findByIdAndPassword(String loginId, String password) {
        return store.values().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .filter(member -> member.getPassword().equals(password))
                .findAny();
    }

    public Optional<Member> findById(long id) {
        return store.values().stream()
                .filter(member -> member.getId() == id)
                .findAny();
    }

    public boolean existsByLoginId(String loginId) {
        return store.values().stream()
                .anyMatch(member -> member.getLoginId().equals(loginId));
    }
}
