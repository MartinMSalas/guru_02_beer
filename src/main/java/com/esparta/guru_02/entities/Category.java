package com.esparta.guru_02.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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
@AllArgsConstructor
@EnableJpaAuditing
@Entity
@Builder
public class Category {

    // =========================================================
    // PRIMARY KEY (technical) - internal identifier
    // =========================================================
    @Id
    @UuidGenerator
    @JdbcTypeCode(org.hibernate.type.SqlTypes.CHAR)
    @Column(name = "category_id", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID categoryId;


    private String categoryDescription;

    // =========================================================
    // RELATIONSHIPS
    // =========================================================
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id"))
    private Set<Beer> beers = new HashSet<>();


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
