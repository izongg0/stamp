package odin.stamp.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountSignInGetReqDto {

    @Schema(description = "이메일", example = "izongg11@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "wdsa4608")
    private String password;

    public AccountSignInGetReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountSignInGetDto toAccountSignInGetDto() {
        return new AccountSignInGetDto(email, password);
    }
}
