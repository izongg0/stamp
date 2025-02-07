package odin.stamp.user.account.exception;

import odin.stamp.common.exception.ValidationIllegalArgumentException;
import org.springframework.validation.Errors;

public class MemberSignUpValidationIllegalArgumentException
        extends ValidationIllegalArgumentException {
    public MemberSignUpValidationIllegalArgumentException(Errors errors) {
        super(errors);
    }
}
