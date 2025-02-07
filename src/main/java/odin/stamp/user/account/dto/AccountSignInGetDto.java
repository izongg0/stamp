package odin.stamp.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountSignInGetDto {
    private String email;
    private String password;
}
