package com.kifiya.PromoQuoter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemAlreadyExistsException extends AbstractServiceException {

    public ItemAlreadyExistsException(String message) {
        super(message, ErrorCode.ITEM_ALREADY_EXISTS);
    }

}
