package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/quote")
    public ResponseEntity<CartQuoteResponse> getQuote(@Valid @RequestBody CartRequest cartRequest) {

        return ResponseEntity.ok(cartService.getQuote(cartRequest));
    }

    @PostMapping("/confirm")
    public ResponseEntity<CartConfirmationResponse> confirmOrder(
            @Valid @RequestBody CartRequest cartRequest,
            @RequestHeader(value = "Idempotency-Key", required = true) UUID idempotencyKey) {
        try {
            CartConfirmationResponse response = cartService.confirmOrder(cartRequest, idempotencyKey);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build(); // 409 Conflict for stock issues
        }
    }
}
