package odin.stamp.user.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.user.account.Account;

@RequiredArgsConstructor
@Getter
public class AccountCreateReqDto {
    private String name;
    private String email;
    private String password;

    public AccountCreateReqDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account toAccount() {
        return Account.of(name, email, password);
    }
}
