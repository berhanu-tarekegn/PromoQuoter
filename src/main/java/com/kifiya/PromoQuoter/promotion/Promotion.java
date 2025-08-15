package com.kifiya.PromoQuoter.promotion;

import com.kifiya.PromoQuoter.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(name = "unique_constraint_by_type_and_product_id", columnNames = {"promotion_type", "product_id"}),
        @UniqueConstraint(name = "unique_constraint_by_name", columnNames = "name"),
        @UniqueConstraint(name = "unique_constraint_by_type_and_product_category", columnNames = {"promotion_type", "category"})},
        indexes = {
        @Index(name = "promotion_idx_id", columnList = "id", unique = true)
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "promotionType", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Promotion extends AbstractEntity {

    @NotBlank(message = "error.validation.promotion.name.required")
    private String name;

    private boolean active = true;
}
