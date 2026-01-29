package com.esparta.guru_02.entities;

import com.esparta.guru_02.model.BeerStyle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */

@Entity
@Table(name = "beer")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

    @Id
    @GeneratedValue
    @Column(length = 36, updatable = false, nullable = false)
    private UUID beerId;

    @Column(nullable = false, length = 255)
    private String beerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BeerStyle beerStyle;

    @Column(nullable = false, unique = true, length = 50)
    private String upc;

    @Column(nullable = false)
    private Integer quantityOnHand;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    /* =========================
        AUDITING
        ========================= */

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant lastModifiedDate;

    /* =========================
       OPTIMISTIC LOCKING
       ========================= */

    @Version
    private Long version;
}
