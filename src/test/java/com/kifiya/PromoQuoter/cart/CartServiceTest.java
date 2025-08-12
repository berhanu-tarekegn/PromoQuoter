package com.kifiya.PromoQuoter.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCart() {
        Cart cart = new Cart();

        when(cartRepository.save(cart)).thenReturn(cart);

        Cart savedCart = cartService.saveCart(cart);
        assertNotNull(savedCart);
    }

    @Test
    void testGetCartById() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.getCartById(cartId);
        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getId());
    }
}
