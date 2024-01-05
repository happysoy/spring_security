package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientEmptyPasswordCheck extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyPasswordCheck();

    private ClientEmptyPasswordCheck() {
        super(AuthErrorCode.CLIENT_EMPTY_PASSWORD_CHECK);
    }
}
