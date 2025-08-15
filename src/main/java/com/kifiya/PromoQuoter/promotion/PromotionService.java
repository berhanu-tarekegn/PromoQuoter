package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    Promotion savePromotion(PromotionRequest promotionRequest);

    List<Promotion> getAllPromotions();

    Promotion getPromotionById(UUID id);

    void applyPromotions(CartContext context);
}
