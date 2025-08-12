package com.kifiya.PromoQuoter.promotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kifiya.PromoQuoter.promotion.PromotionServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePromotion() {
        Promotion promotion = new PercentOffCategoryPromotion();
        promotion.setName("Category Discount");

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
