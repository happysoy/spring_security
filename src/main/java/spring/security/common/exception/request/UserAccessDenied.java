package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class UserAccessDenied extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserAccessDenied();

    private UserAccessDenied() {
        super(AuthErrorCode.USER_ACCESS_DENIED);
    }

}
