package com.esparta.guru_02.entities;

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

import java.time.Instant;
import java.util.UUID;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @UuidGenerator
    @JdbcTypeCode(org.hibernate.type.SqlTypes.CHAR)
    @Column(name = "customer_id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private UUID customerId;

    @Column(nullable = false, length = 255)
    private String customerName;


    @Column(length = 255)
    private String email;
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
