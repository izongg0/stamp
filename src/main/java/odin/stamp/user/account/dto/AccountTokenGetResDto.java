package odin.stamp.user.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountTokenGetResDto {

    private final String accessToken;

    private final String refreshToken;

}
