package roomescape.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull(message = "이름은 필수로 입력해야 합니다.")
    private final String name;
    @NotNull(message = "로그인 아이디는 필수로 입력해야 합니다.")
    private final String loginId;
    @NotNull(message = "비밀번호는 필수로 입력해야 합니다.")
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
