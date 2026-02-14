package com.esparta.guru_02.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/*
 * Author: M
 * Date: 13-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BeerOrderShipment {

    public BeerOrderShipment(UUID beerOrderShipmentId, String shipmentTrackingNumber, BeerOrder beerOrder,
                             Instant createdDate, Instant lastModifiedDate, Long version) {

        this.beerOrderShipmentId = beerOrderShipmentId;
        this.shipmentTrackingNumber = shipmentTrackingNumber;
        setBeerOrder(beerOrder);
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    // =========================================================
    // PRIMARY KEY (technical) - internal identifier
    // =========================================================
    @Id
    @UuidGenerator
    @JdbcTypeCode(org.hibernate.type.SqlTypes.CHAR)
    @Column(name = "beer_order_shipment_id", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID beerOrderShipmentId;

    private String shipmentTrackingNumber;

    // =========================================================
    // RELATIONSHIPS
    // =========================================================
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_order_id", nullable = false, unique = true)
    private BeerOrder beerOrder;

    // =========================================
    // Bidirectional sync logic
    // =========================================

    public void setBeerOrder(BeerOrder beerOrder) {

        this.beerOrder = beerOrder;
        beerOrder.setBeerOrderShipment(this);


    }

    // =========================================================
    // AUDITING
    // =========================================================
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Instant lastModifiedDate;

    // =========================================================
    // OPTIMISTIC LOCKING
    // =========================================================
    @Version
    @Column(name = "version")
    private Long version;

}
