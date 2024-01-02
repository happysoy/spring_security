package spring.security.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.security.dto.response.CodeMessageResponse;
import spring.security.services.MessageResponseService;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice {
    private final MessageResponseService messageResponseService;

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CodeMessageResponse customException(CustomException customException) {
        ExceptionStatus status = customException.getExceptionStatus();
        return messageResponseService.getExceptionResponse(status);
    }
}
