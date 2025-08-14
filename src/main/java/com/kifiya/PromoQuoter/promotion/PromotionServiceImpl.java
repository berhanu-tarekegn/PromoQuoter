package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.ProductRepository;
import com.kifiya.PromoQuoter.promotion.dto.BuyXGetYPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PercentOffCategoryPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService, PromotionRuleEngine {

    private static final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    private final PromotionRepository promotionRepository;
    private final PromotionRuleFactory promotionRuleFactory;
    private final ProductRepository productRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionRuleFactory promotionRuleFactory, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.promotionRuleFactory = promotionRuleFactory;
        this.productRepository = productRepository;
    }

    @Override
    public Promotion savePromotion(PromotionRequest promotionRequest) {
        Promotion promotion;

        if (promotionRequest instanceof PercentOffCategoryPromotionRequest p) {
            PercentOffCategoryPromotion entity = new PercentOffCategoryPromotion();
            entity.setName(p.name);
            entity.setCategory(p.category);
            entity.setDiscountPercentage(p.discountPercentage);
            promotion = entity;

        } else if (promotionRequest instanceof BuyXGetYPromotionRequest b) {
            BuyXGetYPromotion entity = new BuyXGetYPromotion();
            entity.setName(b.name);
            entity.setBuyQuantity(b.buyQuantity);
            entity.setGetQuantityFree(b.getQuantity);

            Product product = productRepository.findById(b.productId)
                    .orElseThrow(() -> new ItemNotFoundException("Product not found with ID: " + b.productId));
            entity.setProductId(product.getId());

            promotion = entity;

        } else {
            throw new ItemNotFoundException("Unknown promotion type");
        }
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getPromotionById(UUID id) {
        return promotionRepository.findById(id).orElse(null);
    }

    public void apply(PromotionContext context) {
        List<Promotion> promotions = promotionRepository.findAll();

        List<PromotionRuleEngine> rules = promotions.stream()
                .map(promotionRuleFactory::createRule)
                .sorted(Comparator.comparingInt(PromotionRuleEngine::getOrder))
                .collect(Collectors.toList());

        for (PromotionRuleEngine rule : rules) {
            rule.apply(context);
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
