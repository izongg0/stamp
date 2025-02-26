package odin.stamp.common.exception.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Locale;

/**
 * 공통 에러 Response 상세
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorsResponseDetail {

    private final String message;
    private final String field;

    /**
     * ObjectError로 에러 상세 응답 객체 생성
     * @param e ObjectError
     * @param messageSource Spring MessageSource
     * @return ErrorResponseDetail
     */
    protected static ErrorsResponseDetail of(ObjectError e, MessageSource messageSource) {
        return new ErrorsResponseDetail(
                getMessage(e, messageSource),
                e instanceof FieldError ? ((FieldError) e).getField() : null
        );
    }

    /**
     * ObjectError의 Codes 우선순위에 따라 Localize된 Message를 린턴
     * @param objectError Spring ObjectError
     * @param messageSource Spring MessageSource
     * @return Error Message
     */
    private static String getMessage(ObjectError objectError, MessageSource messageSource) {
        try {
            return messageSource.getMessage(objectError, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            return getMessage("invalid.noMessage", messageSource);
        }
    }

    /**
     * Code의 Localize된 Message를 리턴
     * @param code 에러 코드
     * @param messageSource Spring MessageSource
     * @return Error message
     */
    private static String getMessage(String code, MessageSource messageSource) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }


}
