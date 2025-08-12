package com.kifiya.PromoQuoter.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/quote")
    public ResponseEntity<Cart> quoteCart(@RequestBody Cart cart) {
        // Implement logic to calculate the cart's final price
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Cart> confirmCart(@RequestBody Cart cart) {
        // Implement logic to validate stock and reserve inventory
        return ResponseEntity.ok(cart);
    }
}
