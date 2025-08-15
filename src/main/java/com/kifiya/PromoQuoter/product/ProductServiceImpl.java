package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.cart.dto.CartItemRequest;
import com.kifiya.PromoQuoter.exception.DatabaseConstraintViolationException;
import com.kifiya.PromoQuoter.exception.ItemAlreadyExistsException;
import com.kifiya.PromoQuoter.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        log.info("Saving product: {}", product.getName());
        try {
            Product savedProduct = productRepository.save(product);
            log.debug("Saved product details: {}", savedProduct);
            return savedProduct;
        } catch (DataIntegrityViolationException | ConstraintViolationException ex) {
            log.error("Database conflict with existing product", ex);
            throw new DatabaseConstraintViolationException("Database constraint violation: " + ex.getMessage());
        }

    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        log.debug("Fetched products: {}", products);
        return products;
    }

    @Override
    public Product getById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product not found: " + id));
    }

    @Override
    public Map<Product, Integer> fetchProductsForCart(List<CartItemRequest> items) {
        List<UUID> ids = items.stream().map(CartItemRequest::getProductId).toList();
        Map<UUID, Product> productMap = productRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return items.stream()
                .collect(Collectors.toMap(
                        item -> productMap.get(item.getProductId()),
                        CartItemRequest::getQuantity
                ));
    }

    @Transactional
    @Override
    public void reserveStock(UUID productId, int qty) {
        Product product = getById(productId);
        if (product.getStock() < qty) {
            throw new StockUnavailableException("Not enough stock for product: " + product.getName());
        }
        product.setStock(product.getStock() - qty);
        productRepository.saveAndFlush(product);
    }
}
