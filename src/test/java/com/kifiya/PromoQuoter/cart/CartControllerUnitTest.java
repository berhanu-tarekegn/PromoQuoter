package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class CartControllerUnitTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuoteThrowsException() {
        CartRequest cartRequest = new CartRequest();

        doThrow(new RuntimeException("Service exception")).when(cartService).getQuote(any(CartRequest.class));

        ResponseEntity<CartQuoteResponse> response = cartController.getQuote(cartRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testConfirmOrderThrowsException() {
        CartRequest cartRequest = new CartRequest();
        UUID idempotencyKey = UUID.randomUUID();

        doThrow(new RuntimeException("Service exception")).when(cartService).confirmOrder(any(CartRequest.class), any(UUID.class));

        ResponseEntity<CartConfirmationResponse> response = cartController.confirmOrder(cartRequest, idempotencyKey);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetQuote() {
        CartRequest cartRequest = new CartRequest();
        CartQuoteResponse expectedResponse = new CartQuoteResponse();

        when(cartService.getQuote(any(CartRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<CartQuoteResponse> response = cartController.getQuote(cartRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testConfirmOrder() {
        CartRequest cartRequest = new CartRequest();
        UUID idempotencyKey = UUID.randomUUID();
        CartConfirmationResponse expectedResponse = new CartConfirmationResponse();

        when(cartService.confirmOrder(any(CartRequest.class), any(UUID.class))).thenReturn(expectedResponse);

        ResponseEntity<CartConfirmationResponse> response = cartController.confirmOrder(cartRequest, idempotencyKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}
