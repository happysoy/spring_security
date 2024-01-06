package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class UserNotAuth extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserNotAuth();

    private UserNotAuth() {
        super(AuthErrorCode.USER_NOT_AUTH);
    }

}
