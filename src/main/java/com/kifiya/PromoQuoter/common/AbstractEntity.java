package com.kifiya.PromoQuoter.common;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@JsonFilter("promoQuoterFilter")
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Version
    @JsonIgnore
    private Long version;
}
