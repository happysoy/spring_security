package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientEmptyUsername extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyUsername();

    public ClientEmptyUsername() {
        super(AuthErrorCode.CLIENT_EMPTY_USERNAME);
    }
}
