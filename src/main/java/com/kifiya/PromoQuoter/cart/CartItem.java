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

    // Getters and setters
}
