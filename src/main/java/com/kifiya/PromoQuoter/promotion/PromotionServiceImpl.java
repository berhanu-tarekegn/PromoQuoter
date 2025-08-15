package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.exception.DatabaseConstraintViolationException;
import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import com.kifiya.PromoQuoter.product.Product;
import com.kifiya.PromoQuoter.product.ProductRepository;
import com.kifiya.PromoQuoter.promotion.dto.BuyXGetYPromotionRequest;
import com.kifiya.PromoQuoter.promotion.dto.PromotionRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PromotionServiceImpl implements PromotionService {

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

        log.info("Saving promotion: {}", promotionRequest.getName());

        Promotion promotion = promotionRequest.toEntity();

        if (promotion instanceof BuyXGetYPromotion b && promotionRequest instanceof BuyXGetYPromotionRequest br) {
            Product product = productRepository.findById(br.productId)
                    .orElseThrow(() -> new ItemNotFoundException("Product not found with id: " + br.productId));
            b.setProductId(product.getId());
        }
        try {
            Promotion savedPromotion = promotionRepository.save(promotion);
            log.debug("Saved promotion details: {}", savedPromotion);
            return savedPromotion;
        } catch (DataIntegrityViolationException | ConstraintViolationException ex) {
           log.error("Database conflict with existing promotion", ex.getMessage());
            throw new DatabaseConstraintViolationException(ex.getMessage());
        }

    }

    @Override
    public List<Promotion> getAllPromotions() {
        log.info("Fetching all promotions");
        List<Promotion> promotions = promotionRepository.findAll();
        log.debug("Fetched promotions: {}", promotions);
        return promotions;
    }

    @Override
    public Promotion getPromotionById(UUID id) {
        log.info("Fetching promotion by ID: {}", id);
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) {
            log.warn("Promotion not found for ID: {}", id);
            throw new ItemNotFoundException("Promotion not found for ID: " + id);
        }
        log.debug("Fetched promotion details: {}", promotion);
        return promotion;
    }

    @Override
    public void applyPromotions(CartContext context) {
        List<Promotion> promotions = promotionRepository.findAll();

        List<PromotionRuleEngine> rules = promotions.stream()
                .map(promotionRuleFactory::createRule)
                .sorted(Comparator.comparingInt(PromotionRuleEngine::getOrder))
                .collect(Collectors.toList());

        for (PromotionRuleEngine rule : rules) {
            rule.apply(context);
        }
    }
}
