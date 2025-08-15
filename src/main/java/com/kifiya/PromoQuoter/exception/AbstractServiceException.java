package com.kifiya.PromoQuoter.exception;

public abstract class AbstractServiceException extends RuntimeException {

    final int errorCode;

    public AbstractServiceException(String message, int errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
