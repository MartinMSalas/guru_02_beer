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
@AllArgsConstructor
@Builder
public class BeerOrder {

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
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

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

    // =========================================================
    // Helper Methods
    // =========================================================
    // ✅ bidirectional sync
    public void setCustomer(Customer customer) {
        // optional: remove from old customer if you support reassignment
        // if (this.customer != null) this.customer.getBeerOrders().remove(this);

        this.customer = customer;
        if (customer != null) {
            customer.getBeerOrders().add(this);
        }
    }

    // ✅ Lombok hook: customize builder behavior
    public BeerOrder build() {

        BeerOrder order = new BeerOrder();

        order.setBeerOrderId(this.beerOrderId);
        order.setCustomerRef(this.customerRef);

        // preserve default if not set
        order.setBeerOrderLines(
                this.beerOrderLines != null
                        ? this.beerOrderLines
                        : new HashSet<>()
        );

        order.setCreatedDate(this.createdDate);
        order.setLastModifiedDate(this.lastModifiedDate);
        order.setVersion(this.version);

        if (this.customer != null) {
            order.setCustomer(this.customer);
        }

        return order;
    }

}

