
package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;

import java.util.UUID;

public interface CartService {

    CartQuoteResponse getQuote(CartRequest cartRequest);

    CartConfirmationResponse confirmOrder(CartRequest request, UUID idempotencyKey);

}
