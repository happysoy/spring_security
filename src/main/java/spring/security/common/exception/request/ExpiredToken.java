package spring.security.common.exception.request;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class ExpiredToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ExpiredToken();

    private ExpiredToken() {
        super(AuthErrorCode.EXPIRED_TOKEN);
    }
}
