package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;
import roomescape.controller.dto.request.LoginRequest;
import roomescape.controller.dto.request.RegisterRequest;
import roomescape.domain.member.Member;
import roomescape.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public long login(LoginRequest request) {
        return memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword()).
                orElseThrow(() -> new RoomEscapeException(ErrorCode.MEMBER_NOT_FOUND)).getId();
    }

    public Member register(RegisterRequest request) {
        return memberRepository.save(Member.create(request.getName(), request.getLoginId(), request.getPassword()));
    }

    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
