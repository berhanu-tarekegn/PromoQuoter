package com.kifiya.PromoQuoter.promotion.dto;

import com.kifiya.PromoQuoter.promotion.BuyXGetYPromotion;
import com.kifiya.PromoQuoter.promotion.Promotion;
import com.kifiya.PromoQuoter.promotion.PromotionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class BuyXGetYPromotionRequest extends PromotionRequest {

    @NotNull(message = "Product id required")
    public UUID productId;

    @NotNull(message = "Buy quantity is required")
    @Positive(message = "Buy quantity has to be positive")
    public Integer buyQuantity;

    @NotNull(message = "The free quantity is required")
    @Positive(message = "The free quantity has to be positive")
    public Integer getQuantityFree;

    @Override
    public BuyXGetYPromotion toEntity() {
        BuyXGetYPromotion entity = new BuyXGetYPromotion();
        entity.setName(this.name);
        entity.setBuyQuantity(this.buyQuantity);
        entity.setGetQuantityFree(this.getQuantityFree);
        entity.setActive(this.isActive);
        return entity;
    }
}