package com.kifiya.PromoQuoter.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(
        indexes = {
                @Index(name = "product_idx_uuid", columnList = "id", unique = true)
        }
)
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "error.validation.product.name.required")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "error.validation.product.category.required")
    private ProductCategory category;

    @NotNull(message = "error.validation.product.price.required")
    @DecimalMin(value = "0.0", inclusive = true, message = "error.validation.product.price.min")
    private BigDecimal price;

    @Min(value = 0, message = "error.validation.product.stock.min")
    private int stock;

    @Version
    private int version;
}
