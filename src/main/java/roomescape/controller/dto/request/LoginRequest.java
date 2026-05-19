package roomescape.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    private final String loginId;

    @NotNull
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
