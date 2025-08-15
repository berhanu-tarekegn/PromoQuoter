package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.cart.dto.CartItemRequest;
import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProductServiceUnitTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void saveProduct_shouldReturnSavedProduct() {
        Product product = new Product();
        product.setName("Test Product");

        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.saveProduct(product);
        assertEquals(product.getName(), saved.getName());
    }

    @Test
    void getById_shouldThrowItemNotFoundException() {
        UUID id = UUID.randomUUID();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> productService.getById(id));
    }

    @Test
    void fetchProductsForCart_shouldMapProducts() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        CartItemRequest itemRequest = new CartItemRequest();
        itemRequest.setProductId(id);
        itemRequest.setQuantity(3);

        Mockito.when(productRepository.findAllById(List.of(id))).thenReturn(List.of(product));

        Map<Product, Integer> result = productService.fetchProductsForCart(List.of(itemRequest));
        assertEquals(3, result.get(product));
    }

    @Test
    void reserveStock_shouldThrowWhenStockUnavailable() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setStock(1);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        assertThrows(StockUnavailableException.class, () -> productService.reserveStock(id, 5));
    }
}
