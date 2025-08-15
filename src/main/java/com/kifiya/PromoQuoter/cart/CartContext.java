package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.StockUnavailableException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Data
@Slf4j
public class CartContext {
    private final Map<Product, Integer> cartItems;
    private BigDecimal currentTotal;
    private final List<String> appliedPromotionNames = new ArrayList<>();
    private final Map<UUID, BigDecimal> lineItemDiscounts = new HashMap<>();

    public CartContext(Map<Product, Integer> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            this.cartItems = Collections.emptyMap();
            this.currentTotal = BigDecimal.ZERO;
            return;
        }

        this.cartItems = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Integer qty = entry.getValue();

            if (product == null) {
                throw new ItemNotFoundException("Cart contains a null product.");
            }
            if (qty == null || qty <= 0) {
                throw new StockUnavailableException("Invalid quantity for product: " + product.getName());
            }

            this.cartItems.put(product, qty);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(qty)));
        }

        this.currentTotal = total;
    }

    public void addDiscount(BigDecimal discount, String promotionName) {
        log.info("Applying discount for promotion '{}': {}", promotionName, discount);
        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            this.currentTotal = this.currentTotal.subtract(discount);
            this.appliedPromotionNames.add(promotionName);
        } else {
            log.debug("No discount applied for promotion '{}'", promotionName);
        }
    }
}
