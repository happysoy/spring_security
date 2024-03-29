package spring.security.common.exception.docs;

import spring.security.common.annotation.ExceptionDoc;
import spring.security.common.annotation.ExplainError;
import spring.security.common.annotation.SwaggerException;
import spring.security.common.exception.GlobalCustomException;
import spring.security.common.exception.request.EmptyToken;
import spring.security.common.exception.request.ExpiredToken;
import spring.security.common.exception.request.UserAccessDenied;
import spring.security.common.exception.response.ClientUnauthorizedException;

@ExceptionDoc
public class OtherServiceExceptionDocs implements SwaggerException {
    @ExplainError("refresh token이 만료되어 재로그인이 필요한 경우")
    public GlobalCustomException 토큰_만료 = ExpiredToken.EXCEPTION;

    @ExplainError("헤더에 토큰이 없는 경우")
    public GlobalCustomException 토큰_없음 = EmptyToken.EXCEPTION;

    @ExplainError("사용자 인증이 되지 않은 경우 - header에 인증 정보가 담기지 않음 or 로그인 안함")
    public GlobalCustomException 인증_안됨 = ClientUnauthorizedException.EXCEPTION;

    @ExplainError("접근 권한이 없는 경우")
    public GlobalCustomException 접근_권한_없음 = UserAccessDenied.EXCEPTION;


}
