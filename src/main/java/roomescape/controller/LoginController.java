package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.request.LoginRequest;
import roomescape.controller.dto.request.RegisterRequest;
import roomescape.controller.dto.response.TokenResponse;
import roomescape.global.JwtTokenProvider;
import roomescape.service.MemberService;

@RestController
public class LoginController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        memberService.register(request);
        return "회원가입에 성공했습니다.";
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        long memberId = memberService.login(loginRequest);

        String token = jwtTokenProvider.createToken(memberId);
        return new TokenResponse(token);
    }
}
