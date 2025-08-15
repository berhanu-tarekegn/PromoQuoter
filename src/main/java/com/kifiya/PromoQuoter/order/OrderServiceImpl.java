package com.kifiya.PromoQuoter.order;

import com.kifiya.PromoQuoter.cart.CartContext;
import com.kifiya.PromoQuoter.cart.dto.CartConfirmationResponse;
import com.kifiya.PromoQuoter.cart.dto.CartRequest;
import com.kifiya.PromoQuoter.common.utils.HashUtil;
import com.kifiya.PromoQuoter.exception.ItemAlreadyExistsException;
import com.kifiya.PromoQuoter.product.ProductRepository;
import com.kifiya.PromoQuoter.product.ProductService;
import com.kifiya.PromoQuoter.promotion.PromotionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PromotionService  promotionService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, PromotionService promotionService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.promotionService = promotionService;
    }

    @Transactional
    @Override
    public CartConfirmationResponse createOrder(CartRequest request, UUID idempotencyKey) {
        log.info("Creating order for request {}", request);

        String requestHash = HashUtil.hashRequestObject(request);

        Order existingOrder = orderRepository.findByIdempotencyKey(idempotencyKey).orElse(null);

        if (existingOrder != null && existingOrder.getRequestHash().equals(requestHash)) {

            log.info("Order already exists for request {}", requestHash);
            return CartConfirmationResponse.builder()
                    .orderId(existingOrder.getId())
                    .finalPrice(existingOrder.getFinalPrice())
                    .build();
        }


        request.getItems().forEach(item -> productService.reserveStock(item.getProductId(), item.getQuantity()));

        // Calculate final price
        var cartItems = productService.fetchProductsForCart(request.getItems());
        var context = new CartContext(cartItems);
        promotionService.applyPromotions(context);

        // Save order
        Order order = new Order();
        order.setIdempotencyKey(idempotencyKey);
        order.setRequestHash(requestHash);
        order.setFinalPrice(context.getCurrentTotal());
        Order saved = orderRepository.save(order);

        return CartConfirmationResponse.builder()
                .orderId(saved.getId())
                .finalPrice(saved.getFinalPrice())
                .build();
    }
}
