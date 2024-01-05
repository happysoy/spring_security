package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientInvalidPassword extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientInvalidPassword();

    private ClientInvalidPassword() {
        super(AuthErrorCode.CLIENT_INVALID_PASSWORD);
    }

}
