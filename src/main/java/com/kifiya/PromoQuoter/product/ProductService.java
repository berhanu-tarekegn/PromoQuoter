package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.cart.dto.CartItemRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getById(UUID id);

    Map<Product, Integer> fetchProductsForCart(List<CartItemRequest> items);

    void reserveStock(UUID productId, int qty);
}
