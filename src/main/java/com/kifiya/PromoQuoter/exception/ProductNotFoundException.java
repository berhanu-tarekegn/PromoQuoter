package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.exception.AbstractServiceException;

public class ProductNotFoundException extends AbstractServiceException {

    public ProductNotFoundException(String message) {
        super(message, 404);
    }
}
