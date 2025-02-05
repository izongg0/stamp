package odin.stamp.common.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ValidationIllegalArgumentException extends IllegalArgumentException {

    private final Errors errors;

    public ValidationIllegalArgumentException(Errors errors) {
        this("", errors);
    }

    public ValidationIllegalArgumentException(String s, Errors errors) {
        super(s);
        this.errors = errors;
    }
}
