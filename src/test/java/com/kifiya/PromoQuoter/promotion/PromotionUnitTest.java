package com.kifiya.PromoQuoter.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PromotionUnitTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testBuyXGetYPromotionSerialization() throws Exception {
        BuyXGetYPromotion promotion = new BuyXGetYPromotion();
        promotion.setName("Buy 1 Get 1 Free");
        promotion.setActive(true);

        String json = objectMapper.writeValueAsString(promotion);
        BuyXGetYPromotion deserializedPromotion = objectMapper.readValue(json, BuyXGetYPromotion.class);

        assertEquals(promotion.getName(), deserializedPromotion.getName());
        assertTrue(deserializedPromotion.isActive());
    }

    @Test
    void testPercentOffCategoryPromotionSerialization() throws Exception {
        PercentOffCategoryPromotion promotion = new PercentOffCategoryPromotion();
        promotion.setName("10% Off Electronics");
        promotion.setActive(true);

        String json = objectMapper.writeValueAsString(promotion);
        PercentOffCategoryPromotion deserializedPromotion = objectMapper.readValue(json, PercentOffCategoryPromotion.class);

        assertEquals(promotion.getName(), deserializedPromotion.getName());
        assertTrue(deserializedPromotion.isActive());
    }
}
