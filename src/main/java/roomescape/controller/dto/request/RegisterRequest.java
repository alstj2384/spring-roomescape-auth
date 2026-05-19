package roomescape.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull
    private final String name;
    @NotNull
    private final String loginId;
    @NotNull
    private final String password;

    public RegisterRequest(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
