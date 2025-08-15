package com.kifiya.PromoQuoter.exception;

public class ErrorCode {

    public static final int ITEM_ALREADY_EXISTS = 1000;
    public static final int ITEM_NOT_FOUND = 1001;
    public static final int DATABASE_CONSTRAINT_VIOLATION = 1002;
    public static final int MISSING_REQUEST_HEADER = 1003;
    public static final int STOCK_UNAVAILABLE_ERROR = 409;

    private ErrorCode(){}
}
