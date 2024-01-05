package spring.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.security.common.annotation.ExplainError;

import java.lang.reflect.Field;
import java.util.Objects;

import static spring.security.common.AuthStatic.BAD_REQUEST;
import static spring.security.common.AuthStatic.UNAUTHORIZED;


@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode{

    /**
     * 2100: request 에러
     */
    CLIENT_EMPTY_EMAIL(BAD_REQUEST, "AUTH_2100_1", "이메일을 입력해주세요"),
    CLIENT_EMPTY_USERNAME(BAD_REQUEST, "AUTH_2100_2", "이름을 입력해주세요"),
    CLIENT_EMPTY_PASSWORD(BAD_REQUEST, "AUTH_2100_3", "비밀번호를 입력해주세요"),
    CLIENT_EMPTY_PASSWORD_CHECK(BAD_REQUEST, "AUTH_2100_4", "비밀번호 확인을 입력해주세요"),

    CLIENT_INVALID_EMAIL(BAD_REQUEST, "AUTH_2101_1", "이메일 형식이 올바르지 않습니다"),
    CLIENT_INVALID_PASSWORD(BAD_REQUEST, "AUTH_2101_2", "비밀번호는 8글자 이상 20글자 이하여야 합니다"),

    EXPIRED_TOKEN(UNAUTHORIZED, "AUTH_2102_1", "토큰이 만료되었습니다"),
    USER_ACCESS_DENIED(UNAUTHORIZED, "AUTH_2012_2", "접근 권한이 없습니다"),

    /**
     * 2200: response 에러
     */
    DUPLICATE_EMAIL(BAD_REQUEST, "AUTH_2200_1", "중복된 이메일입니다"),
    INCORRECT_PASSWORD_CHECK(BAD_REQUEST, "AUTH_2200_2", "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    FAIL_LOGIN(BAD_REQUEST, "AUTH_2200_3", "아이디 또는 비밀번호를 확인해주세요"),
    USER_NOT_FOUND(BAD_REQUEST, "AUTH_2200_4", "존재하지 않는 사용자입니다");

    private Integer status;
    private String code;
    private String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(status)
                .code(code)
                .reason(reason)
                .build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
