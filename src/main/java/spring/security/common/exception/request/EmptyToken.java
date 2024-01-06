package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class EmptyToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new EmptyToken();

    private EmptyToken() {
        super(AuthErrorCode.EMPTY_TOKEN);
    }
}
