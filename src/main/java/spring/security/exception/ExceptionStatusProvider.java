package spring.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class ExceptionStatusProvider {

    public static void throwError(Errors errors) {
        String code = errors.getFieldError().getCode();
        String field = errors.getFieldError().getField();
        log.info("code={}", code);
        log.info("field={}", field);
        throw new CustomException(ExceptionStatusProvider.getExceptionStatus(code, field));
    }

    public static ExceptionStatus getExceptionStatus(String code, String field) {
        switch (code) {
            case "NotBlank" -> {
                switch (field) {
                    case "email" -> {
                        return ExceptionStatus.USER_EMPTY_EMAIL;
                    }
                    case "password" -> {
                        return ExceptionStatus.USER_EMPTY_PASSWORD;
                    }
                    case "passwordCheck" -> {
                        return ExceptionStatus.USER_EMPTY_PASSWORD_CHECK;
                    }
                    case "username" -> {
                        return ExceptionStatus.USER_EMPTY_USERNAME;
                    }
                }
            }
            case "Length" -> { // "Pattern
                switch (field) {
                    case "username" -> {
                        return ExceptionStatus.USER_INVALID_NAME;
                    }
                    case "password" -> {
                        return ExceptionStatus.USER_INVALID_PASSWORD;
                    }
                }
            }
            case "Email" ->{
                return ExceptionStatus.USER_INVALID_EMAIL;
            }
        }
        return ExceptionStatus.RESPONSE_ERROR;
    }
}