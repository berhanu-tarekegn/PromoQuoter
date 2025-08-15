package com.kifiya.PromoQuoter.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartItemRequest;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CartService cartService;

    @Test
    void getQuote_shouldReturnCartQuoteResponse() throws Exception {
        CartQuoteResponse mockResponse = CartQuoteResponse.builder()
                .cartLineItems(Collections.emptyList())
                .subtotal(BigDecimal.valueOf(100))
                .totalDiscount(BigDecimal.valueOf(10))
                .finalPrice(BigDecimal.valueOf(90))
                .appliedPromotions(List.of("Promo1"))
                .build();

        Mockito.when(cartService.getQuote(any(CartRequest.class)))
                .thenReturn(mockResponse);

        CartRequest cartRequest = new CartRequest();

        cartRequest.setItems(List.of(new CartItemRequest(UUID.randomUUID(), 34)));

        mockMvc.perform(post("/cart/quote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void confirmOrder_shouldReturnConfirmationResponse() throws Exception {
        UUID idempotencyKey = UUID.randomUUID();
        CartConfirmationResponse mockResponse = CartConfirmationResponse.builder()
                .orderId(UUID.randomUUID())
                .finalPrice(BigDecimal.valueOf(90))
                .build();

        Mockito.when(cartService.confirmOrder(any(CartRequest.class), eq(idempotencyKey)))
                .thenReturn(mockResponse);

        CartRequest cartRequest = new CartRequest();

        cartRequest.setItems(List.of(new CartItemRequest(UUID.randomUUID(), 34)));

        mockMvc.perform(post("/cart/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", idempotencyKey)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void confirmOrder_shouldReturnConflict_whenIllegalStateExceptionThrown() throws Exception {
        UUID idempotencyKey = UUID.randomUUID();
        Mockito.when(cartService.confirmOrder(any(CartRequest.class), eq(idempotencyKey)))
                .thenThrow(new IllegalStateException("Out of stock"));

        CartRequest cartRequest = new CartRequest();

        cartRequest.setItems(List.of(new CartItemRequest(UUID.randomUUID(), 34)));

        mockMvc.perform(post("/cart/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Idempotency-Key", idempotencyKey)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void getQuote_shouldReturnBadRequest_whenItemsEmpty() throws Exception {
        CartRequest invalidRequest = new CartRequest();

        mockMvc.perform(post("/cart/quote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
