package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.exception.StockUnavailableException;
import com.kifiya.PromoQuoter.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
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

    @Override
    public Cart getCartById(UUID id) {
        return cartRepository.findById(id).orElse(null);
    }
}

