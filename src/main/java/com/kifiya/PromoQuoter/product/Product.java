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
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
