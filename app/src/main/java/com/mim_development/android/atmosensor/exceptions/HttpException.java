package com.mim_development.android.atmosensor.exceptions;

/**
 * Created by luther stanton on 6/4/15.
 */
public class HttpException extends OperationException {

    private int httpStatus;
    private String errorResponseContent;

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorResponseContent() {
        return errorResponseContent;
    }

    public HttpException(int httpStatus, String errorResponseContent) {
        this.httpStatus = httpStatus;
        this.errorResponseContent = errorResponseContent;
    }

    public HttpException(int httpStatus, String errorResponseContent, Throwable throwable) {
        super(errorResponseContent, throwable);
        this.httpStatus = httpStatus;
        this.errorResponseContent = errorResponseContent;
    }
}
