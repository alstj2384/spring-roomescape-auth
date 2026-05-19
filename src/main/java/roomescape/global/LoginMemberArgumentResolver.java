package roomescape.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;
import roomescape.domain.member.Member;
import roomescape.service.MemberService;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String LOGIN_MEMBER_ID = "loginMemberId";

    private final MemberService memberService;

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginMember.class);
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && isMemberType;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);

        if(session == null){
            throw new RoomEscapeException(ErrorCode.UNAUTHORIZED);
        }

        Long memberId = (Long) session.getAttribute(LOGIN_MEMBER_ID);

        if(memberId == null){
            throw new RoomEscapeException(ErrorCode.UNAUTHORIZED);
        }

        return memberService.find(memberId);
    }
}
