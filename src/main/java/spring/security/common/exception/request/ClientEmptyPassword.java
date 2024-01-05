package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientEmptyPassword extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyPassword();

    public ClientEmptyPassword() {
        super(AuthErrorCode.CLIENT_EMPTY_PASSWORD);
    }
}
