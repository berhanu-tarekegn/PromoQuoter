package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartItemRequest;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.order.OrderService;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.ProductService;
import com.kifiya.PromoQuoter.promotion.PromotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class CartServiceUnitTest {

    private ProductService productService;
    private PromotionService promotionService;
    private OrderService orderService;
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        promotionService = Mockito.mock(PromotionService.class);
        orderService = Mockito.mock(OrderService.class);
        cartService = new CartServiceImpl(productService, promotionService, orderService);
    }

    @Test
    void getQuote_shouldReturnQuoteResponse() {
        UUID productId = UUID.randomUUID();
        CartItemRequest itemRequest = new CartItemRequest();
        itemRequest.setProductId(productId);
        itemRequest.setQuantity(2);

        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(50));

        Map<Product, Integer> cartItems = Map.of(product, 2);
        Mockito.when(productService.fetchProductsForCart(any())).thenReturn(cartItems);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setItems(List.of(itemRequest));

        CartQuoteResponse response = cartService.getQuote(cartRequest);

        assertEquals(1, response.getCartLineItems().size());
        assertEquals(product.getName(), response.getCartLineItems().get(0).getProductName());
        assertEquals(2, response.getCartLineItems().get(0).getQuantity());
    }

    @Test
    void confirmOrder_shouldReturnCartConfirmationResponse() {
        UUID idempotencyKey = UUID.randomUUID();
        CartRequest cartRequest = new CartRequest();

        CartConfirmationResponse mockResponse = new CartConfirmationResponse();
        mockResponse.setOrderId(UUID.randomUUID());
        Mockito.when(orderService.createOrder(cartRequest, idempotencyKey)).thenReturn(mockResponse);

        CartConfirmationResponse response = cartService.confirmOrder(cartRequest, idempotencyKey);

        assertEquals(mockResponse.getOrderId(), response.getOrderId());
    }
}
