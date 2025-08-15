package com.kifiya.PromoQuoter.product;

import com.kifiya.PromoQuoter.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category"}),
        indexes = {@Index(name = "product_idx_uuid", columnList = "id", unique = true)})
@Getter
@Setter
@NoArgsConstructor
public class Product extends AbstractEntity implements Serializable {

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

}
