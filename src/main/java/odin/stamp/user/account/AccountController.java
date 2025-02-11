package odin.stamp.user.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.AuthenticationInfo;
import odin.stamp.common.authentication.annotations.NoAuthentication;
import odin.stamp.common.exception.ValidationIllegalArgumentException;
import odin.stamp.user.account.dto.AccountCreateReqDto;
import odin.stamp.user.account.dto.AccountSignInGetReqDto;
import odin.stamp.user.account.dto.AccountSignInGetResDto;
import odin.stamp.user.account.dto.AccountTokenGetResDto;
import odin.stamp.user.account.exception.MemberSignUpValidationIllegalArgumentException;
import odin.stamp.user.account.validator.UserSignUpReqValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final UserSignUpReqValidator userSignUpReqValidator;

    /**
     * 인증 정보
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<AuthenticationInfo> getAuthenticationInfo(AuthenticationInfo authInfo) {
        return ResponseEntity.ok(authInfo);
    }

    @PostMapping("/sign-up")
    @NoAuthentication
    public ResponseEntity<Void> signIn(
            @RequestBody AccountCreateReqDto accountCreateReqDto
            , BindingResult bindingResult
    ) {

        userSignUpReqValidator.validate(accountCreateReqDto,bindingResult);
        if(bindingResult.hasErrors()) {
            throw new MemberSignUpValidationIllegalArgumentException(bindingResult);
        }

        Account account = accountService.create(accountCreateReqDto);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인
     * @param accountSignInGetReqDto
     * @return
     */
    @PostMapping("/sign-in")
    @NoAuthentication
    public ResponseEntity<AccountSignInGetResDto> signIn(
            @RequestBody @Validated AccountSignInGetReqDto accountSignInGetReqDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }

        Token token = accountService.signIn(accountSignInGetReqDto.toAccountSignInGetDto());

        return ResponseEntity.ok(AccountSignInGetResDto.of(token));
    }

    /**
     * 접근 토큰 재발급
     * @param refreshToken
     * @return
     */
    @PostMapping("/token/re-issuance")
    @NoAuthentication
    public ResponseEntity<AccountTokenGetResDto> AccessTokenReIssuance(
            @RequestHeader(name="Authorization") String refreshToken) {

        Token token = accountService.reIssuance(refreshToken);

        return ResponseEntity.ok(new AccountTokenGetResDto(
                token.getAccessToken(), token.getRefreshToken()));
    }


}
