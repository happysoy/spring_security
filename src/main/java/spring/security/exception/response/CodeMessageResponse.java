package spring.security.exception.response;

public record CodeMessageResponse(Boolean isSuccess, int code, String message) {
    public static CodeMessageResponse of(Boolean isSuccess, int code, String message) {
        return new CodeMessageResponse(isSuccess, code, message);
    }
}
