package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.product.Product;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class BuyXGetYRule implements PromotionRuleEngine {
    private final BuyXGetYPromotion promotion;

    public BuyXGetYRule(BuyXGetYPromotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public void apply(CartContext context) {
        for (Map.Entry<Product, Integer> entry : context.getCartItems().entrySet()) {
            if (entry.getKey().getId().equals(promotion.getProductId())) {
                int quantity = entry.getValue();
                int buyQty = promotion.getBuyQuantity();
                int getYQty = promotion.getGetQuantityFree();

                if (quantity >= buyQty + getYQty) {
                    log.info("Promotion has been successfully built.");
                    int numberOfDeals = quantity / (buyQty + getYQty);
                    BigDecimal discount = entry.getKey().getPrice().multiply(BigDecimal.valueOf((long) numberOfDeals * getYQty));
                    context.addDiscount(discount, promotion.getName());
                } else {
                    log.info("Promotion has not been successfully, insufficient stock.");
                }
                return; // Rule only applies to one product
            }
        }
    }

    @Override
    public int getOrder() { return PromotionType.BUY_X_GET_Y.order; }
}

