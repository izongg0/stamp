package odin.stamp.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 공통 에러 Response
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorsResponse {

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ErrorsResponseDetail> errors;

    /**
     * Exception message로 ErrorsResponse 생성
     * @param message
     * @return
     */
    public static ErrorsResponse from(String message) {
        return new ErrorsResponse(message, new ArrayList<>());
    }

    /**
     * Validation 실패에 따른 Errors로 ErrorsResponse 생성
     * @param errors
     * @param messageSource
     * @return
     */
    public static ErrorsResponse of(Errors errors, MessageSource messageSource) {

        List<ErrorsResponseDetail> errorsResponseDetails = errors.getAllErrors().stream().map(e -> ErrorsResponseDetail.of(e, messageSource)).toList();

        return new ErrorsResponse(
                messageSource.getMessage("invalid", null, Locale.getDefault()),
                errorsResponseDetails
        );
    }

}
