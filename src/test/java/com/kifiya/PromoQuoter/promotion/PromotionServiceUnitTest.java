package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.promotion.dto.PercentOffCategoryPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceUnitTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePromotion() {
        PromotionRequest promotion = new PercentOffCategoryPromotionRequest();
        promotion.setName("Category Discount");
        promotion.setActive(true);


        when(promotionRepository.save(promotion)).thenReturn(promotion);

        Promotion savedPromotion = promotionService.savePromotion(promotion);
        assertNotNull(savedPromotion);
        assertEquals("Category Discount", savedPromotion.getName());
    }

    @Test
    void testGetPromotionById() {
        UUID promotionId = UUID.randomUUID();
        Promotion promotion = new PercentOffCategoryPromotion();
        promotion.setId(promotionId);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion));

        Promotion foundPromotion = promotionService.getPromotionById(promotionId);
        assertNotNull(foundPromotion);
        assertEquals(promotionId, foundPromotion.getId());
    }
}
