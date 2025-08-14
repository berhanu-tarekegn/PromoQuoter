package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.exception.AbstractServiceException;

public class PromotionNotFoundException extends AbstractServiceException {

    public PromotionNotFoundException(String message) {
        super(message, 404);
    }
}
