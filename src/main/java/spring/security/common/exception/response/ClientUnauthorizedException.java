package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ClientUnauthorizedException extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientUnauthorizedException();

    private ClientUnauthorizedException() {
        super(AuthErrorCode.CLIENT_UNAUTHORIZED);
    }

}
