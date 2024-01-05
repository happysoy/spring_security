package spring.security.common.exception.response;

public record DataMessageResponse<T>(Boolean isSuccess, int code, String message, T data) {
    public static <T> DataMessageResponse<T> of(Boolean isSuccess, int code, String message, T data) {
        return new DataMessageResponse<>(isSuccess, code, message, data);
    }
}
