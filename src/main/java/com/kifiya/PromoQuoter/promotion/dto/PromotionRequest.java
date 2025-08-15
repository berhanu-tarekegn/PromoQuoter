package com.kifiya.PromoQuoter.promotion.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kifiya.PromoQuoter.promotion.Promotion;
import com.kifiya.PromoQuoter.promotion.PromotionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "promotionType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PercentOffCategoryPromotionRequest.class, name = "PERCENT_OFF_CATEGORY"),
        @JsonSubTypes.Type(value = BuyXGetYPromotionRequest.class, name = "BUY_X_GET_Y")
})
@Data
public abstract class PromotionRequest {

    @NotBlank(message = "promotion name required")
    public String name;

    public boolean isActive =  true;

    public abstract Promotion toEntity();
}
