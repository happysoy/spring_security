package spring.security.common.exception;

import java.time.LocalDateTime;

public record SuccessResponse(
        boolean success,
        int status,
        Object data,
        LocalDateTime timeStamp) {

    public SuccessResponse(int status, Object data) {
        this(true, status, data, LocalDateTime.now());
    }

}

