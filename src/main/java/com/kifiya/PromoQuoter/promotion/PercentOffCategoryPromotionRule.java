package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class PercentOffCategoryPromotionRule implements PromotionRuleEngine {

    private final PercentOffCategoryPromotion promotion;

    public PercentOffCategoryPromotionRule(PercentOffCategoryPromotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public void apply(CartContext context) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : context.getCartItems().entrySet()) {
            Product product = entry.getKey();
            if (product.getCategory().name().equalsIgnoreCase(promotion.getCategory().name())) {
                BigDecimal originalPrice = product.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
                BigDecimal discount = originalPrice.multiply(promotion.getDiscountPercentage())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                totalDiscount = totalDiscount.add(discount);
                context.getAppliedPromotionNames().add(promotion.getName());
            }
        }
    }

    @Override
    public int getOrder() { return PromotionType.PERCENT_OFF_CATEGORY.order; } // Apply category discounts first
}

