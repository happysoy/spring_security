package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientEmptyEmail extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyEmail();

    private ClientEmptyEmail() {
        super(AuthErrorCode.CLIENT_EMPTY_EMAIL);
    }
}
