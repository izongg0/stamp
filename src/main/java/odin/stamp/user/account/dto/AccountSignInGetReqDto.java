package odin.stamp.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountSignInGetReqDto {

    private String email;

    private String password;

    public AccountSignInGetReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountSignInGetDto toAccountSignInGetDto() {
        return new AccountSignInGetDto(email, password);
    }
}
