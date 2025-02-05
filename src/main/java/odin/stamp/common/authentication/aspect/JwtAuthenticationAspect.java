package odin.stamp.common.authentication.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.authentication.AuthenticationInfo;
import odin.stamp.common.authentication.TokenProvider;
import odin.stamp.common.exception.JwtAuthenticationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationAspect {

    private final TokenProvider tokenProvider;

    @Around("execution(* odin.stamp..*Controller.*(..)) && " +
            "!@annotation(odin.stamp.common.authentication.annotations.NoAuthentication)")
    public Object authenticate(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String token = tokenProvider.resolveToken(request);

        if (token == null || !tokenProvider.validateToken(token)) {
            throw new JwtAuthenticationException("authenticationFailed.account.token");
        }

        Object[] args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a instanceof AuthenticationInfo ? tokenProvider.getAuthenticationInfoByAccessToken(token) : a)
                .toArray();

        return joinPoint.proceed(args);
    }
}
