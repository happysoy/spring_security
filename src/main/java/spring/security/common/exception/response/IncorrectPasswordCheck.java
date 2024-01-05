package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class IncorrectPasswordCheck extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new IncorrectPasswordCheck();

    private IncorrectPasswordCheck() {
        super(AuthErrorCode.INCORRECT_PASSWORD_CHECK);
    }
}
