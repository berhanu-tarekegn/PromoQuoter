package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.ProductRepository;
import com.kifiya.PromoQuoter.promotion.dto.PercentOffCategoryPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PromotionServiceUnitTest {

    private PromotionRepository promotionRepository;
    private PromotionRuleFactory promotionRuleFactory;
    private ProductRepository productRepository;
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setUp() {
        promotionRepository = Mockito.mock(PromotionRepository.class);
        promotionRuleFactory = Mockito.mock(PromotionRuleFactory.class);
        productRepository = Mockito.mock(ProductRepository.class);
        promotionService = new PromotionServiceImpl(promotionRepository, promotionRuleFactory, productRepository);
    }

    @Test
    void savePromotion_shouldReturnSavedPromotion() {
        PercentOffCategoryPromotionRequest request = new PercentOffCategoryPromotionRequest();
        request.setName("Promo 1");
        request.setDiscountPercentage(BigDecimal.valueOf(10));

        Promotion promo = request.toEntity();
        Mockito.when(promotionRepository.save(any(Promotion.class))).thenReturn(promo);

        Promotion saved = promotionService.savePromotion(request);
        assertEquals(request.getName(), saved.getName());
    }

    @Test
    void getPromotionById_shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();
        Mockito.when(promotionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> promotionService.getPromotionById(id));
    }

    @Test
    void applyPromotions_shouldCallRules() {
        Promotion promo = new PercentOffCategoryPromotion();
        promo.setId(UUID.randomUUID());

        PromotionRuleEngine ruleEngine = Mockito.mock(PromotionRuleEngine.class);
        Mockito.when(promotionRepository.findAll()).thenReturn(List.of(promo));
        Mockito.when(promotionRuleFactory.createRule(promo)).thenReturn(ruleEngine);

        Map<Product, Integer> cartItems = Map.of();
        CartContext context = new CartContext(cartItems);

        promotionService.applyPromotions(context);

        Mockito.verify(ruleEngine).apply(context);
    }
}
