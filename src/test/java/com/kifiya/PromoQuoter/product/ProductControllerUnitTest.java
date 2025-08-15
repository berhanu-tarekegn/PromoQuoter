package com.kifiya.PromoQuoter.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Autowired
    void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setFilterProvider(
                new SimpleFilterProvider()
                        .addFilter("promoQuoterFilter", SimpleBeanPropertyFilter.serializeAll())
        );
    }


    @Test
    void createProduct_shouldReturnSavedProduct() throws Exception {

        Product mockProduct = new Product();
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setCategory(ProductCategory.BOOKS);
        mockProduct.setName("Test Product");
        mockProduct.setPrice(BigDecimal.valueOf(99.99));
        mockProduct.setStock(10);

        Mockito.when(productService.saveProduct(any(Product.class)))
                .thenReturn(mockProduct);


        Product requestProduct = new Product();
        requestProduct.setName("Test Product");
        requestProduct.setCategory(ProductCategory.BOOKS);
        requestProduct.setPrice(BigDecimal.valueOf(99.99));
        requestProduct.setStock(10);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestProduct)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProduct)));
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() throws Exception {
        Product p = new Product();
        p.setId(UUID.randomUUID());
        p.setName("Test Product");
        p.setPrice(BigDecimal.valueOf(99.99));
        p.setStock(10);

        Product p1 = new Product();
        p1.setId(UUID.randomUUID());
        p1.setName("Test Product");
        p1.setPrice(BigDecimal.valueOf(99.99));
        p1.setStock(10);

        List<Product> mockProducts = List.of(p, p1);

        Mockito.when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProducts)));
    }

    @Test
    void createProduct_shouldReturnBadRequest_whenNameIsMissing() throws Exception {
        // Missing 'name'

        Product invalidProduct = new Product();
        invalidProduct.setId(UUID.randomUUID());
        invalidProduct.setPrice(BigDecimal.valueOf(-99.99));
        invalidProduct.setStock(10);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_shouldReturnBadRequest_whenPriceIsNegative() throws Exception {
        // Negative price
        Product invalidProduct = new Product();
        invalidProduct.setId(UUID.randomUUID());
        invalidProduct.setName("Test Product");
        invalidProduct.setPrice(BigDecimal.valueOf(-99.99));
        invalidProduct.setStock(10);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllProducts_shouldReturnEmptyList_whenNoProductsExist() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
