package odin.stamp.common.authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthenticationInfo {

    /** 계정 ID */
    private final Long accountId;

    /** 이메일 */
    private final String email;

    /** 사용자명 */
    private final String name;



    public static AuthenticationInfo of(Long accountId, String email, String name) {
        return new AuthenticationInfo(accountId, email, name);
    }
}