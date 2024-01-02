package spring.security.services;

import org.springframework.stereotype.Service;
import spring.security.dto.response.CodeMessageResponse;
import spring.security.dto.response.DataMessageResponse;
import spring.security.exception.ExceptionStatus;

@Service
public class MessageResponseService {

    public CodeMessageResponse getSuccessResponse() {
        return CodeMessageResponse.of(true, 1000, "요청 성공");
    }

    public CodeMessageResponse getFailResponse() {
        return CodeMessageResponse.of(false, 4000, "요청 실패");
    }

    public CodeMessageResponse getExceptionResponse(ExceptionStatus status) {
        return CodeMessageResponse.of(status.isSuccess(), status.getCode(), status.getMessage());
    }

    public <T> DataMessageResponse<T> getDataResponse(T data) {
        return DataMessageResponse.of(true, 1000, "요청 성공", data);
    }

}
