package roomescape.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.request.LoginRequest;
import roomescape.controller.dto.request.RegisterRequest;
import roomescape.service.MemberService;

@RestController
public class LoginController {
    private static final String LOGIN_MEMBER_ID = "loginMemberId";
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request){
        memberService.register(request);
        return "회원가입에 성공했습니다.";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request){
        long memberId = memberService.login(loginRequest);
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER_ID, memberId);

        return "로그인에 성공했습니다.";
    }
}
