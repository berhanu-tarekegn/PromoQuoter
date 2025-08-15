package com.kifiya.PromoQuoter.exception;

public class DatabaseConstraintViolationException extends AbstractServiceException {

    public DatabaseConstraintViolationException(String message) {
        super(message, ErrorCode.DATABASE_CONSTRAINT_VIOLATION);

    }
}
