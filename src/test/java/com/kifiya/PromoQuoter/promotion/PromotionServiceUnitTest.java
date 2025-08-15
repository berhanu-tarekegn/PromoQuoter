package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
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
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePromotion() {
        PromotionRequest request = new PromotionRequest();
        Promotion expectedPromotion = new Promotion();

        when(promotionRepository.save(any(Promotion.class))).thenReturn(expectedPromotion);

        Promotion result = promotionService.savePromotion(request);

        assertNotNull(result);
        assertEquals(expectedPromotion, result);
    }

    @Test
    void testGetAllPromotions() {
        List<Promotion> promotions = List.of(new Promotion());
        when(promotionRepository.findAll()).thenReturn(promotions);

        List<Promotion> result = promotionService.getAllPromotions();

        assertNotNull(result);
        assertEquals(promotions.size(), result.size());
    }

    @Test
    void testGetPromotionById() {
        UUID id = UUID.randomUUID();
        Promotion promotion = new Promotion();
        when(promotionRepository.findById(id)).thenReturn(Optional.of(promotion));

        Promotion result = promotionService.getPromotionById(id);

        assertNotNull(result);
        assertEquals(promotion, result);
    }

    @Test
    void testGetPromotionByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(promotionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> promotionService.getPromotionById(id));
    }
}
