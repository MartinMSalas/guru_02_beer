package com.esparta.guru_02.entities;

import com.esparta.guru_02.model.BeerStyle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Entity
@Table(
        name = "beer",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_beer_external_id", columnNames = "external_beer_id"),
                @UniqueConstraint(name = "uk_beer_upc", columnNames = "upc")
        },
        indexes = {
                @Index(name = "ix_beer_external_id", columnList = "external_beer_id"),
                @Index(name = "ix_beer_upc", columnList = "upc")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

    // =========================================================
    // PRIMARY KEY (technical) - internal identifier
    // =========================================================
    @Id
    @UuidGenerator
    @JdbcTypeCode(org.hibernate.type.SqlTypes.CHAR)
    @Column(name = "beer_id", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID beerId;

    // =========================================================
    // BUSINESS KEY (from CSV) - external stable identifier
    // Maps from BeerCSVRecord column "id"
    // =========================================================
    @Column(name = "external_beer_id")
    private Integer externalBeerId;

    // =========================================================
    // DOMAIN FIELDS
    // =========================================================
    @Column(name = "beer_name", nullable = false, length = 255)
    private String beerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "beer_style", nullable = false, length = 50)
    private BeerStyle beerStyle;

    @Column(name = "upc",  length = 50)
    private String upc;

    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    // =========================================================
    // RELATIONSHIP
    // =========================================================
    @Builder.Default
    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines =  new HashSet<>();

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

    @Override
    public String toString() {
        return "Beer{" +
                "beerId=" + beerId +
                ", beerName='" + beerName + '\'' +
                ", beerStyle=" + beerStyle +
                ", createdDate=" + createdDate +
                ", externalBeerId=" + externalBeerId +
                ", lastModifiedDate=" + lastModifiedDate +
                ", price=" + price +
                ", quantityOnHand=" + quantityOnHand +
                ", upc='" + upc + '\'' +
                ", version=" + version +
                '}';
    }
}