package com.kifiya.PromoQuoter.cart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class CartRequest {
    @NotEmpty
    @Valid
    private List<CartItemRequest> items;
}
