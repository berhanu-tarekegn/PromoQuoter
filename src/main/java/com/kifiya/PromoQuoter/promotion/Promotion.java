package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.common.AbstractEntity;
import com.kifiya.PromoQuoter.product.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table( indexes = {
        @Index(name = "promotion_idx_id", columnList = "id", unique = true)
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "promotion_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BuyXGetYPromotion.class, name = "BUY_X_GET_Y"),
        @JsonSubTypes.Type(value = PercentOffCategoryPromotion.class, name = "PERCENT_OFF_CATEGORY")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "promotion_type", discriminatorType = DiscriminatorType.STRING)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Promotion extends AbstractEntity {

    @NotBlank(message = "error.validation.promotion.name.required")
    private String name;

    private boolean active = true;
}
