package spring.security.common.exception;

public interface BaseErrorCode {

    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
