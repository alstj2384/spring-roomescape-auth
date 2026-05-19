package roomescape.repository;

import java.util.Optional;
import roomescape.domain.member.Member;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByIdAndPassword(String id, String password);
    Optional<Member> findById(long userId);
}
