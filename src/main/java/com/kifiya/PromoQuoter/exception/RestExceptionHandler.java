package com.kifiya.PromoQuoter.exception;

import com.kifiya.PromoQuoter.product.StockUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(StockUnavailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse stockUnavailableException(StockUnavailableException ex){
        return new ErrorResponse("LOW STOCK",ex.getMessage(), ErrorCode.STOCK_UNAVAILABLE_ERROR, OffsetDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex){
        return new ErrorResponse("BAD_REQUEST", ex.getMessage(), 400, OffsetDateTime.now());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse itemNotFoundException(ItemNotFoundException ex){
        log.error(ex.getMessage());
        return new ErrorResponse("ITEM NOT FOUND", ex.getMessage(), 404, OffsetDateTime.now());
    }

    @ExceptionHandler(DatabaseConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse databaseConstraintViolationException(DatabaseConstraintViolationException ex){
        log.error("Database conflict with existing product" + ex.getMessage());
        return new ErrorResponse("UNIQUE CONSTRAINT VIOLATION", ex.getMessage(), ErrorCode.DATABASE_CONSTRAINT_VIOLATION, OffsetDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        return new ErrorResponse("CONFLICT", ex.getMessage(), ErrorCode.DATABASE_CONSTRAINT_VIOLATION, OffsetDateTime.now());

    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse itemAlreadyExistsException(ItemAlreadyExistsException ex){
        log.error(ex.getMessage());
        return new ErrorResponse("ITEM ALREADY EXISTS", ex.getMessage(), ErrorCode.ITEM_ALREADY_EXISTS, OffsetDateTime.now());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingRequestHeaderException(MissingRequestHeaderException ex){
        log.error(ex.getMessage());
        return new ErrorResponse("REQUEST HEADER MISSING", ex.getMessage(), ErrorCode.MISSING_REQUEST_HEADER, OffsetDateTime.now());
    }
}
