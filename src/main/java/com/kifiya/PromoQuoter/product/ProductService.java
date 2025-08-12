package com.kifiya.PromoQuoter.product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(UUID id);
}
