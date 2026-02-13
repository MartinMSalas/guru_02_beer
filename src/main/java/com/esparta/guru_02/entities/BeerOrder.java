package com.esparta.guru_02.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
 * Author: M
 * Date: 09-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Entity
@Table(
        name = "beer_order"

)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@Builder
public class BeerOrder {

    public BeerOrder(UUID beerOrderId, String customerRef, Customer customer, Set<BeerOrderLine> beerOrderLines, Instant createdDate, Instant lastModifiedDate, Long version) {

        this.beerOrderId = beerOrderId;
        this.customerRef = customerRef;
        this.setCustomer(customer);
        this.beerOrderLines = beerOrderLines;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    // =========================================================
    // PRIMARY KEY (technical) - internal identifier
    // =========================================================
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "beer_order_id", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID beerOrderId;

    @Column(name = "customer_ref")
    private String customerRef;

    // =========================================================
    // RELATIONSHIPS
    // =========================================================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.PERSIST)
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();


    // =========================================
    // Bidirectional sync logic
    // =========================================

    public void setCustomer(Customer customer) {

        this.customer = customer;
        customer.getBeerOrders().add(this);

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

