package com.kifiya.PromoQuoter.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setStock(10);

        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    void testGetProductById() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(productId);
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
    }
}
