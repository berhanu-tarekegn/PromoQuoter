package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.exception.StockUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart saveCart(Cart cart) {
        // Example logic to check stock availability
        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new StockUnavailableException("Product not found: " + item.getProductId()));

            if (product.getStock() < item.getQuantity()) {
                throw new StockUnavailableException("Insufficient stock for product: " + product.getName());
            }
        }
        return cartRepository.save(cart);
    }

    public Cart getCartById(UUID id) {
        return cartRepository.findById(id).orElse(null);
    }
}
