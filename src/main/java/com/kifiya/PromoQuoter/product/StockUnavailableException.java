package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.exception.AbstractServiceException;
import com.kifiya.PromoQuoter.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StockUnavailableException extends AbstractServiceException {

    public StockUnavailableException(String message) {
        super(message, ErrorCode.STOCK_UNAVAILABLE_ERROR);
    }
}
