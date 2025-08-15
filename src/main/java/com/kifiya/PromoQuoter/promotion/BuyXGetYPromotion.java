package com.kifiya.PromoQuoter.promotion;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@DiscriminatorValue("BUY_X_GET_Y")
@Getter
@Setter
@NoArgsConstructor
public class BuyXGetYPromotion extends Promotion {

    @NotNull
    private UUID productId;

    @Min(value = 1, message = "Buy quantity is required")
    private int buyQuantity;

    @Min(value = 1, message = "Get free quantity is required")
    private int getQuantityFree;

}
