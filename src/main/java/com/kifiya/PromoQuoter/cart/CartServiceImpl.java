package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.exception.StockUnavailableException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.rule.RuleEngine;
import com.kifiya.PromoQuoter.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final RuleEngine ruleEngine;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, RuleEngine ruleEngine) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.ruleEngine = ruleEngine;
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
        Cart processedCart = ruleEngine.applyPromotions(cart);
        return cartRepository.save(processedCart);
    }

    @Override
    public Cart getCartById(UUID id) {
        return cartRepository.findById(id).orElse(null);
    }
}

