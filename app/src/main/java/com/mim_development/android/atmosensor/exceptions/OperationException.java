package com.mim_development.android.atmosensor.exceptions;

/**
 * Created by luther stanton on 6/4/15.
 */
public class OperationException extends Exception {

    public OperationException() {
    }

    public OperationException(String detailMessage) {
        super(detailMessage);
    }

    public OperationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OperationException(Throwable throwable) {
        super(throwable);
    }
}
