package uk.gov.hmcts.reform.ccd.client.exception;

public class InvalidCaseDataException extends RuntimeException {

    public InvalidCaseDataException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
