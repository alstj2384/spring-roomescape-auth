package roomescape.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;

public class LoginCheckInterceptor implements HandlerInterceptor {
    private static final String LOGIN_MEMBER_ID = "loginMemberId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(LOGIN_MEMBER_ID) == null){
            throw new RoomEscapeException(ErrorCode.UNAUTHORIZED);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
