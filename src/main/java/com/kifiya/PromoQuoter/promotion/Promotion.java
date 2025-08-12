package com.kifiya.PromoQuoter.promotion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table( indexes = {
        @Index(name = "promotion_idx_id", columnList = "id", unique = true)
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "promotion_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Promotion {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "error.validation.promotion.name.required")
    private String name;

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
}
