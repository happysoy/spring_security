package spring.security.common.exception.response;

import spring.security.common.exception.AuthErrorCode;
import spring.security.common.exception.GlobalCustomException;

public class DuplicateEmail extends GlobalCustomException {
    public static final GlobalCustomException EXCEPTION = new DuplicateEmail();

    private DuplicateEmail() {
        super(AuthErrorCode.DUPLICATE_EMAIL);
    }
}
