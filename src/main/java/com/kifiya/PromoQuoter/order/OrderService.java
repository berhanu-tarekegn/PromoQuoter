package com.kifiya.PromoQuoter.order;

import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.product.Product;

import java.util.UUID;

public interface OrderService {

    CartConfirmationResponse createOrder(CartRequest request, UUID idempotencyKey);
}
