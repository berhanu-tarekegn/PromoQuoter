package com.kifiya.PromoQuoter.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kifiya.PromoQuoter.product.ProductCategory;
import com.kifiya.PromoQuoter.promotion.dto.PercentOffCategoryPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromotionController.class)
class PromotionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PromotionService promotionService;

    @BeforeEach
    void setUpObjectMapper() {
        // Avoid "Cannot resolve PropertyFilter" error
        objectMapper.setFilterProvider(
                new SimpleFilterProvider()
                        .addFilter("promoQuoterFilter", SimpleBeanPropertyFilter.serializeAll())
        );
    }

    private Promotion createPromotion(UUID id, String name, BigDecimal discount) {
        PercentOffCategoryPromotion promotion = new PercentOffCategoryPromotion();
        promotion.setId(id);
        promotion.setName(name);
        promotion.setDiscountPercentage(discount);
        promotion.setCategory(ProductCategory.BOOKS);
        return promotion;
    }

    private PromotionRequest createPromotionRequest(String name, BigDecimal discount) {
        PercentOffCategoryPromotionRequest request = new PercentOffCategoryPromotionRequest();
        request.setName(name);
        request.setDiscountPercentage(discount);
        request.setCategory(ProductCategory.BOOKS);
        return request;
    }

    @Test
    void createPromotion_shouldReturnSavedPromotion() throws Exception {
        Promotion mockPromotion = createPromotion(UUID.randomUUID(), "Summer Sale", BigDecimal.valueOf(20));

        Mockito.when(promotionService.savePromotion(any(PromotionRequest.class)))
                .thenReturn(mockPromotion);

        PromotionRequest request = createPromotionRequest("Summer Sale", BigDecimal.valueOf(20));

        mockMvc.perform(post("/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockPromotion)));
    }

    @Test
    void getAllPromotions_shouldReturnListOfPromotions() throws Exception {
        List<Promotion> mockPromotions = List.of(
                createPromotion(UUID.randomUUID(), "Winter Sale", BigDecimal.valueOf(15)),
                createPromotion(UUID.randomUUID(), "Flash Deal", BigDecimal.valueOf(50))
        );

        Mockito.when(promotionService.getAllPromotions()).thenReturn(mockPromotions);

        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockPromotions)));
    }

    @Test
    void createPromotion_shouldReturnBadRequest_whenNameIsMissing() throws Exception {
        PromotionRequest invalidRequest = createPromotionRequest(null, BigDecimal.valueOf(20));

        mockMvc.perform(post("/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPromotion_shouldReturnBadRequest_whenDiscountIsNegative() throws Exception {
        PromotionRequest invalidRequest = createPromotionRequest("Bad Deal", BigDecimal.valueOf(-5));

        mockMvc.perform(post("/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPromotions_shouldReturnEmptyList_whenNoneExist() throws Exception {
        Mockito.when(promotionService.getAllPromotions()).thenReturn(List.of());

        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
