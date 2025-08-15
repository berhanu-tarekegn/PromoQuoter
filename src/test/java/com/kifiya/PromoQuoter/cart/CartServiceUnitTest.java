package com.kifiya.PromoQuoter.cart;

import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartQuoteResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartServiceUnitTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuote() {
        CartRequest cartRequest = new CartRequest();
        CartQuoteResponse expectedResponse = new CartQuoteResponse();

        when(cartRepository.getQuote(any(CartRequest.class))).thenReturn(expectedResponse);

        CartQuoteResponse response = cartService.getQuote(cartRequest);

        assertNotNull(response);
    }

    @Test
    void testConfirmOrder() {
        CartRequest cartRequest = new CartRequest();
        UUID idempotencyKey = UUID.randomUUID();
        CartConfirmationResponse expectedResponse = new CartConfirmationResponse();

        when(cartRepository.confirm(any(CartRequest.class), any(UUID.class))).thenReturn(expectedResponse);

        CartConfirmationResponse response = cartService.confirmOrder(cartRequest, idempotencyKey);

        assertNotNull(response);
    }
}
