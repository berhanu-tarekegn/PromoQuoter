package com.kifiya.PromoQuoter.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;

    private int quantity;
}
