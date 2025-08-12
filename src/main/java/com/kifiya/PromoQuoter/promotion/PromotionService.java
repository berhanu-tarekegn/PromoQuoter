package com.kifiya.PromoQuoter.promotion;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    Promotion savePromotion(Promotion promotion);

    List<Promotion> getAllPromotions();

    Promotion getPromotionById(UUID id);
}
