package odin.stamp.common.exception.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.exception.*;
import odin.stamp.common.exception.response.ErrorsResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * 공통 Exception 처리 handler
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class AppExceptionAdvice {

    private final MessageSource messageSource;

    /**
     * JwtAuthenticationException 핸들러
     * @param e
     * @return ApiResponse(ErrorCode)
     */
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorsResponse handleJwtAuthenticationException(JwtAuthenticationException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * DataNotFoundException 핸들러
     * @param e
     * @return ApiResponse(ErrorCode)
     */
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorsResponse handleDataNotFoundException(NotFoundException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * ValidationIllegalArgumentException 핸들러
     * 입력한 필드에 대한 값 검증에 실패할 경우 발생
     * @param e
     */
    @ExceptionHandler(ValidationIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorsResponse handleValidationIllegalArgumentException(ValidationIllegalArgumentException e) {
        return ErrorsResponse.of(e.getErrors(), messageSource);
    }

    /**
     * NotMatchedException 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(NotMatchedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ErrorsResponse handleNotMatchedException(NotMatchedException e) {
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * AlreadyExistsException 핸들러
     * 이미 데이터가 존재하여 데이터를 추가할 수 없을 때 발생
     * @param e
     */
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorsResponse handleAlreadyExistsException(AlreadyExistsException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * AuthenticationFailedException 핸들러
     * 인증 실패 시 발생
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorsResponse handleAuthenticationFailedException(AuthenticationFailedException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * InvalidException 헨들러
     * 로직 검증 실패
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorsResponse handleInvalidException(InvalidException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * HttpMessageNotReadableException 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorsResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ErrorsResponse.from(toMessage("messageNotReadable"));
    }

    /**
     * OutOfRangeException 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(OutOfRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorsResponse handleOutOfRangeException(OutOfRangeException e){
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ErrorsResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorsResponse.from(toMessage("httpRequestMethodNotSupported"));
    }

    @ExceptionHandler(IntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorsResponse handleIntegrityViolationException(IntegrityViolationException e) {
        return ErrorsResponse.from(toMessage(e.getMessage()));
    }

    /**
     * Handle 되지 않은 전체 Exception 처리
     * @param e
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorsResponse HandleUnknownException(Exception e) {
        return ErrorsResponse.from(toMessage("internalServerError"));
    }

    /**
     * code를 message로 변환
     * @param code 에러 code
     * @return message
     */
    private String toMessage(String code) {
        try {
            return messageSource.getMessage(code, null, Locale.getDefault());
        } catch(NoSuchMessageException e) {
            return code;
        }
    }
}
