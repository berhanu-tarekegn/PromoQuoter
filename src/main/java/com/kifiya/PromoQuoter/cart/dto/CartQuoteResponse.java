package com.kifiya.PromoQuoter.cart.dto;

import com.kifiya.PromoQuoter.cart.CartContext;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class CartQuoteResponse {
    private List<CartLineItem> cartLineItems;
    private BigDecimal subtotal;
    private BigDecimal totalDiscount;
    private BigDecimal finalPrice;
    private List<String> appliedPromotions;

    @Data
    @Builder
    public static class CartLineItem {
        private UUID productId;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;
    }

    public static CartQuoteResponse fromContext(CartContext context) {
        var cartLineItems = context.getCartItems().entrySet().stream()
                .map(entry -> CartLineItem.builder()
                        .productId(entry.getKey().getId())
                        .productName(entry.getKey().getName())
                        .quantity(entry.getValue())
                        .unitPrice(entry.getKey().getPrice())
                        .lineTotal(entry.getKey().getPrice().multiply(
                                BigDecimal.valueOf(entry.getValue())))
                        .build())
                .collect(Collectors.toList());

        BigDecimal subtotal = cartLineItems.stream()
                .map(CartLineItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalPrice = context.getCurrentTotal();
        BigDecimal totalDiscount = subtotal.subtract(finalPrice);

        return CartQuoteResponse.builder()
                .cartLineItems(cartLineItems)
                .subtotal(subtotal)
                .totalDiscount(totalDiscount)
                .finalPrice(finalPrice)
                .appliedPromotions(context.getAppliedPromotionNames())
                .build();
    }
}
