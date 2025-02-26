package odin.stamp.user.account.validator;

import odin.stamp.user.account.dto.AccountCreateReqDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSignUpReqValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountCreateReqDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountCreateReqDto dto = (AccountCreateReqDto) target;

        // 이름 검증
        if (!StringUtils.hasText(dto.getName())){
            errors.rejectValue("name", "empty");
        }

        // 이메일 검증
        if (!StringUtils.hasText(dto.getEmail())){
            errors.rejectValue("email","empty");
        } else {
            if (!isValidEmail(dto.getEmail())){
                errors.rejectValue("email", "invalid");
            }
        }

        // 비밀번호 검증
        if (!dto.getPassword().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")){
            errors.rejectValue("password", "invalid");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


}
