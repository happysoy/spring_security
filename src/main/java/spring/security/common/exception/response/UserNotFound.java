package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;
import spring.security.common.exception.request.ClientInvalidEmail;

public class UserNotFound extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserNotFound();

    private UserNotFound() {
        super(AuthErrorCode.USER_NOT_FOUND);
    }

}
