package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.product.ProductCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("PERCENT_OFF_CATEGORY")
public class PercentOffCategoryPromotion extends Promotion {

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductCategory category;

    @NotNull(message = "error.validation.promotion.discountPercentage.required")
    @DecimalMin(value = "0.0", inclusive = false, message = "error.validation.promotion.discountPercentage.min")
    @DecimalMax(value = "1.0", inclusive = false, message = "error.validation.promotion.discountPercentage.max")
    private BigDecimal discountPercentage;

    // Getters and setters
}
