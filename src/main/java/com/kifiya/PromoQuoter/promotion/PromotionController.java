package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public Promotion createPromotion(@Validated @RequestBody PromotionRequest promotion) {
        Promotion persistedPromotion = promotionService.savePromotion(promotion);
        return persistedPromotion;
    }

    @GetMapping
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return promotions;
    }
}
