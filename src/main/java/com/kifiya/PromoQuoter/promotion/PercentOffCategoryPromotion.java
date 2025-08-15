package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.product.ProductCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("PERCENT_OFF_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
public class PercentOffCategoryPromotion extends Promotion {

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductCategory category;

    @NotNull(message = "error.validation.promotion.discountPercentage.required")
    private BigDecimal discountPercentage;

}
