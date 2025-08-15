package com.kifiya.PromoQuoter.promotion;

import org.springframework.stereotype.Component;

@Component
public class PromotionRuleFactory {
    public PromotionRuleEngine createRule(Promotion promotion) {
        if (promotion instanceof PercentOffCategoryPromotion p) {
            return new PercentOffCategoryPromotionRule(p);
        } else if (promotion instanceof BuyXGetYPromotion p) {
            return new BuyXGetYRule(p);
        }
        throw new IllegalArgumentException("Unknown promotion type: " + promotion.getClass().getSimpleName());
    }
}
