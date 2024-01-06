package spring.security.common.exception.docs;

import spring.security.common.annotation.ExceptionDoc;
import spring.security.common.annotation.ExplainError;
import spring.security.common.annotation.SwaggerException;
import spring.security.common.exception.GlobalCustomException;
import spring.security.common.exception.request.*;
import spring.security.common.exception.response.DuplicateEmail;
import spring.security.common.exception.response.FailLogin;
import spring.security.common.exception.response.IncorrectPasswordCheck;

@ExceptionDoc
public class SignInExceptionDocs implements SwaggerException {

    /**
     * request error
     */
    @ExplainError("이메일을 입력하지 않은 경우")
    public GlobalCustomException 이메일_미입력 = ClientEmptyEmail.EXCEPTION;

    @ExplainError("비밀번호를 입력하지 않은 경우")
    public GlobalCustomException 비밀번호_미입력 = ClientEmptyPassword.EXCEPTION;
    @ExplainError("이메일 또는 비밀번호가 잘못된 경우")
    public GlobalCustomException 로그인_실패 = FailLogin.EXCEPTION;




}
