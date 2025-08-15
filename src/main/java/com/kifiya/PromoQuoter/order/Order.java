package com.kifiya.PromoQuoter.order;

import com.kifiya.PromoQuoter.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders",
        indexes = {
        @Index(name = "order_idx_request_hash", columnList = "requestHash", unique = true),
        @Index(name = "order_idx_idempotency_key", columnList = "idempotencyKey", unique = true)
})
public class Order extends AbstractEntity {

    @Column(unique = true, nullable = true)
    private UUID idempotencyKey;

    @Column(unique = true, nullable = true)
    private String requestHash;

    private BigDecimal finalPrice;

}