package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientInvalidEmail extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientInvalidEmail();

    private ClientInvalidEmail() {
        super(AuthErrorCode.CLIENT_INVALID_EMAIL);
    }
}
