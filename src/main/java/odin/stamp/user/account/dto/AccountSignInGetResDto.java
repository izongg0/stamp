package odin.stamp.user.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odin.stamp.user.account.Token;

@Getter
@RequiredArgsConstructor
public class AccountSignInGetResDto {

    private final String accessToken;

    private final String refreshToken;

    public static AccountSignInGetResDto of(Token token) {
        return new AccountSignInGetResDto(token.getAccessToken(), token.getRefreshToken());
    }
}
