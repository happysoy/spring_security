package spring.security.common.exception;

import java.time.LocalDateTime;


public record ErrorResponse(
        boolean success,
        int status,
        String code,
        String reason,
        LocalDateTime timeStamp,
        String path
) {
    public ErrorResponse(ErrorReason errorReason, String path) {
        this(false, errorReason.status(), errorReason.code(), errorReason.reason(), LocalDateTime.now(), path);
    }

    // If you want to keep the other constructor as well
    public ErrorResponse(int status, String code, String reason, String path) {
        this(false, status, code, reason, LocalDateTime.now(), path);
    }
}