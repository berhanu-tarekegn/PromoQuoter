package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import com.kifiya.PromoQuoter.order.OrderService;
import com.kifiya.PromoQuoter.product.ProductService;
import com.kifiya.PromoQuoter.promotion.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductService productService;
    private final PromotionService promotionService;
    private final OrderService orderService;


    @Override
    public CartQuoteResponse getQuote(CartRequest request) {
        var cartItems = productService.fetchProductsForCart(request.getItems());
        var context = new CartContext(cartItems);
        promotionService.applyPromotions(context);
        return CartQuoteResponse.fromContext(context);
    }

    @Override
    public CartConfirmationResponse confirmOrder(CartRequest request, UUID idempotencyKey) {
        return orderService.createOrder(request, idempotencyKey);
    }

}
