package odin.stamp.user.account;

import lombok.RequiredArgsConstructor;
import odin.stamp.common.authentication.TokenProvider;
import odin.stamp.common.authentication.util.PasswordEncoder;
import odin.stamp.common.exception.JwtAuthenticationException;
import odin.stamp.user.account.dto.AccountCreateReqDto;
import odin.stamp.user.account.dto.AccountSignInGetDto;
import odin.stamp.user.account.exception.AccountAlreadyExistsException;
import odin.stamp.user.account.exception.AccountNotFoundException;
import odin.stamp.user.account.exception.UserAlreadyExistsException;
import odin.stamp.user.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public Account create(AccountCreateReqDto accountCreateReqDto) {
        // 이메일 중복 체크
        if(accountRepository.existsByEmail(accountCreateReqDto.getEmail())) {
            throw new AccountAlreadyExistsException();
        }

        String encryptedPassword = passwordEncoder.getEncrypt(accountCreateReqDto.getPassword());

        // 암호화 비밀번호 저장
        Account account = accountCreateReqDto.toAccount();
        account.updatePassword(encryptedPassword);

        // 회원 가입 후 도메인 리턴
        return accountRepository.save(account);
    }

    @Transactional
    public Token signIn(AccountSignInGetDto accountSignInGetDto) {
        String email = accountSignInGetDto.getEmail();

        // 계정 존재 여부 확인
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(AccountNotFoundException::new);

        // 비밀번호 복호화
        if (!passwordEncoder.getEncrypt(accountSignInGetDto.getPassword()).equals(account.getPassword())) {
            throw new AccountNotFoundException();
        }

        // 마지막 로그인 일시 업데이트
        account.updateLastLogonAt();

        // 토큰 발급 및 반환
        return new Token(
                tokenProvider.generateAccessToken(account.getId(), account.getEmail(), account.getName()),
                tokenProvider.generateRefreshToken(account.getEmail())
        );
    }

    /**
     * 토큰 재발급
     * @param refreshToken
     * @return
     */
    public Token reIssuance(String refreshToken) {
        // Refresh token 검증
        if(refreshToken == null || !tokenProvider.validateToken(refreshToken)) {
            throw new JwtAuthenticationException("authenticationFailed.account.token");
        }

        // Refresh token에서 이메일 변환
        Account account = accountRepository
                .findByEmail(tokenProvider.getEmailByRefreshToken(refreshToken))
                .orElseThrow(AccountNotFoundException::new);

        // token 재발급
        return new Token(
                tokenProvider.generateAccessToken(
                        account.getId(),
                        account.getEmail(),
                        account.getName()),
                tokenProvider.generateRefreshToken(
                        account.getEmail()
                )
        );
    }
}
