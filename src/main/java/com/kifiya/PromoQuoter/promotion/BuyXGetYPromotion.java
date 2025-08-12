package com.kifiya.PromoQuoter.promotion;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@DiscriminatorValue("BUY_X_GET_Y")
public class BuyXGetYPromotion extends Promotion {

    @NotNull
    private UUID productId;

    @Min(1)
    private int buyQuantity;

    @Min(1)
    private int getQuantityFree;

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
    public int getGetQuantityFree() {
        return getQuantityFree;
    }
}