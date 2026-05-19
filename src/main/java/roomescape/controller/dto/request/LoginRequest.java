package roomescape.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull(message = "로그인 아이디는 필수로 입력해야 합니다.")
    private final String loginId;

    @NotNull(message = "비밀번호는 필수로 입력해야 합니다.")
    private final String password;

    public LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
