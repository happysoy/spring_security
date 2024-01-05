package spring.security.common.exception;

import spring.security.common.annotation.ExceptionDoc;
import spring.security.common.annotation.ExplainError;
import spring.security.common.annotation.SwaggerException;
import spring.security.common.exception.request.*;
import spring.security.common.exception.response.DuplicateEmail;
import spring.security.common.exception.response.FailLogin;
import spring.security.common.exception.response.IncorrectPasswordCheck;

@ExceptionDoc
public class AuthExceptionDocs implements SwaggerException {

    /**
     * request error
     */
    @ExplainError("이름을 입력하지 않은 경우")
    public GlobalCustomException 이름_미입력 = ClientEmptyUsername.EXCEPTION;
    @ExplainError("이메일을 입력하지 않은 경우")
    public GlobalCustomException 이메일_미입력 = ClientEmptyEmail.EXCEPTION;

    @ExplainError("비밀번호를 입력하지 않은 경우")
    public GlobalCustomException 비밀번호_미입력 = ClientEmptyPassword.EXCEPTION;

    @ExplainError("비밀번호 확인을 입력하지 않은 경우")
    public GlobalCustomException 비밀번호_확인_미입력 = ClientEmptyPasswordCheck.EXCEPTION;

    @ExplainError("이메일 형식이 올바르지 않은 경우")
    public GlobalCustomException 이메일_형식_미준수 = ClientInvalidEmail.EXCEPTION;

    @ExplainError("비밀번호 길이가 올바르지 않은 경우")
    public GlobalCustomException 비밀번호_길이_미준수 = ClientInvalidPassword.EXCEPTION;

    @ExplainError("이메일 또는 비밀번호가 잘못된 경우")
    public GlobalCustomException 로그인_실패 = FailLogin.EXCEPTION;

    @ExplainError("refresh token이 만료되어 재로그인이 필요한 경우")
    public GlobalCustomException 토큰_만료 = ExpiredToken.EXCEPTION;

    @ExplainError("접근 권한이 없는 경우")
    public GlobalCustomException 접근_권한_없음 = UserAccessDenied.EXCEPTION;

    /**
     * response error
     */
    @ExplainError("이미 사용중인 이메일인 경우")
    public GlobalCustomException 이메일_중복 = DuplicateEmail.EXCEPTION;

    @ExplainError("비밀번호와 비밀번호 확인이 일치하지 않는 경우")
    public GlobalCustomException 비밀번호_확인_불일치 = IncorrectPasswordCheck.EXCEPTION;

}
