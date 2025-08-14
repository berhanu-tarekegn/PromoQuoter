package com.kifiya.PromoQuoter.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.kifiya.PromoQuoter.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

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
        Product savedProduct = productRepository.save(product);
        log.debug("Saved product details: {}", savedProduct);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        log.debug("Fetched products: {}", products);
        return products;
    }

    @Override
    public Product getProductById(UUID id) {
        log.info("Fetching product by ID: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            log.debug("Fetched product details: {}", product);
        } else {
            log.warn("Product not found for ID: {}", id);
            throw new ProductNotFoundException("Product not found for ID: " + id);
        }
        return product;
    }
}
