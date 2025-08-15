package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;

public interface PromotionRuleEngine {

    void apply(CartContext context);
    int getOrder();  //Apply lowest first
}
