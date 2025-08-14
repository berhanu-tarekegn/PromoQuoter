package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.ProductRepository;
import com.kifiya.PromoQuoter.promotion.dto.BuyXGetYPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PromotionServiceImplTest {

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private PromotionRuleFactory promotionRuleFactory;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePromotion() {
        PromotionRequest request = new BuyXGetYPromotionRequest();
        request.setName("Test Promotion");

        Promotion promotion = new BuyXGetYPromotion();
        promotion.setName("Test Promotion");

        when(promotionRepository.save(any(Promotion.class))).thenReturn(promotion);

        Promotion savedPromotion = promotionService.savePromotion(request);

        assertNotNull(savedPromotion);
        assertEquals("Test Promotion", savedPromotion.getName());
    }

    @Test
    void testGetAllPromotions() {
        when(promotionRepository.findAll()).thenReturn(List.of(new BuyXGetYPromotion()));

        List<Promotion> promotions = promotionService.getAllPromotions();

        assertFalse(promotions.isEmpty());
    }

    @Test
    void testGetPromotionById() {
        UUID promotionId = UUID.randomUUID();
        Promotion promotion = new BuyXGetYPromotion();
        promotion.setId(promotionId);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion));

        Promotion foundPromotion = promotionService.getPromotionById(promotionId);

        assertNotNull(foundPromotion);
        assertEquals(promotionId, foundPromotion.getId());
    }

    @Test
    void testGetPromotionByIdNotFound() {
        UUID promotionId = UUID.randomUUID();

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> promotionService.getPromotionById(promotionId));
    }
}
