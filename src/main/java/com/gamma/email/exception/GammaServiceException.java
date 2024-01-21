package com.gamma.email.exception;

public class GammaServiceException extends RuntimeException{
    public GammaServiceException(String message) {
        super(message);
    }
    public GammaServiceException(Throwable cause) {
        super(cause);
    }
    public GammaServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public GammaServiceException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
