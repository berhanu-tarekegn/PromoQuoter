package com.kifiya.PromoQuoter.promotion.dto;

import com.kifiya.PromoQuoter.product.ProductCategory;
import com.kifiya.PromoQuoter.promotion.PercentOffCategoryPromotion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class PercentOffCategoryPromotionRequest extends PromotionRequest {

    @NotNull(message = "Product category is required")
    @Enumerated(EnumType.STRING)
    public ProductCategory category;

    @NotNull(message = "Discount percentage required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be positive")
    public BigDecimal discountPercentage;

    @Override
    public PercentOffCategoryPromotion toEntity() {
        PercentOffCategoryPromotion entity = new PercentOffCategoryPromotion();
        entity.setName(this.name);
        entity.setCategory(this.category);
        entity.setDiscountPercentage(this.discountPercentage);
        entity.setActive(this.isActive);
        return entity;
    }
}
