package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;
import spring.security.common.exception.request.ClientInvalidEmail;

public class FailLogin extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new FailLogin();

    private FailLogin() {
        super(AuthErrorCode.FAIL_LOGIN);
    }


}
