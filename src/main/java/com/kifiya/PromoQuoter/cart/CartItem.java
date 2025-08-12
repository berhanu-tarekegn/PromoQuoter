package com.kifiya.PromoQuoter.cart;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class CartItem {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;
    private int quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
