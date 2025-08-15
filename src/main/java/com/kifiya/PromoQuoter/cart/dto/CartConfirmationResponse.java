package com.kifiya.PromoQuoter.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartConfirmationResponse {
    private UUID orderId;
    private BigDecimal finalPrice;
}