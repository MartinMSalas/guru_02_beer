package com.esparta.guru_02.repositories;

import com.esparta.guru_02.configuration.JpaAuditingConfig;
import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.model.BeerStyle;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Author: M
 * Date: 29-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
/*
 * Repository-level tests.
 *
 * Scope:
 * - JPA mappings
 * - Database constraints
 * - Auditing
 * - Optimistic locking
 *
 * Not tested here:
 * - DTO validation
 * - HTTP / controllers
 */
@Import(JpaAuditingConfig.class)
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    TransactionTemplate transactionTemplate;
    @Autowired private TestEntityManager entityManager;
    /* =========================================================
       HAPPY PATH
       ========================================================= */

    @Rollback
    @Transactional
    @Test
    void givenValidBeer_whenSave_thenPersistedWithAuditingAndVersion() {

        Beer beer = Beer.builder()
                .beerName("Valid Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("123456789012")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        Beer saved = beerRepository.saveAndFlush(beer);

        assertThat(saved.getBeerId()).isNotNull();
        assertThat(saved.getCreatedDate()).isNotNull();
        assertThat(saved.getLastModifiedDate()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();
    }

    /* =========================================================
       NOT NULL CONSTRAINTS
       ========================================================= */

    @Test
    void givenBeerWithoutName_whenSave_thenThrowDataIntegrityViolation() {

        Beer beer = Beer.builder()
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("NO_NAME_UPC")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        assertThatThrownBy(() -> beerRepository.saveAndFlush(beer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenBeerWithoutStyle_whenSave_thenThrowDataIntegrityViolation() {

        Beer beer = Beer.builder()
                .beerName("No Style Beer")
                .upc("NO_STYLE_UPC")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        assertThatThrownBy(() -> beerRepository.saveAndFlush(beer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenBeerWithoutUpc_whenSave_thenThrowDataIntegrityViolation() {

        Beer beer = Beer.builder()
                .beerName("No UPC Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        assertThatThrownBy(() -> beerRepository.saveAndFlush(beer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenBeerWithoutQuantity_whenSave_thenThrowDataIntegrityViolation() {

        Beer beer = Beer.builder()
                .beerName("No Quantity Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("NO_QTY_UPC")
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        assertThatThrownBy(() -> beerRepository.saveAndFlush(beer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenBeerWithoutPrice_whenSave_thenThrowDataIntegrityViolation() {

        Beer beer = Beer.builder()
                .beerName("No Price Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("NO_PRICE_UPC")
                .quantityOnHand(10)
                .externalBeerId(999999)
                .build();

        assertThatThrownBy(() -> beerRepository.saveAndFlush(beer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    /* =========================================================
       UNIQUE CONSTRAINT
       ========================================================= */

    @Test
    void givenDuplicateUpc_whenSave_thenThrowDataIntegrityViolation() {

        Beer first = Beer.builder()
                .beerName("Beer One")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("DUPLICATE_UPC")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        Beer second = Beer.builder()
                .beerName("Beer Two")
                .beerStyle(BeerStyle.ALTBIER)
                .upc("DUPLICATE_UPC")
                .quantityOnHand(5)
                .price(new BigDecimal("5.99"))
                .externalBeerId(999998)
                .build();

        beerRepository.saveAndFlush(first);

        assertThatThrownBy(() -> beerRepository.saveAndFlush(second))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    /* =========================================================
       OPTIMISTIC LOCKING
       ========================================================= */
    @Test
    void givenConcurrentUpdates_whenSave_thenThrowOptimisticLockException() {
        // 1. Initial setup: Save a Beer with Version 0
        Beer beer = Beer.builder()
                .beerName("Concurrent Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("LOCK_TEST_UPC")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        Beer savedBeer = beerRepository.saveAndFlush(beer);
        UUID beerId = savedBeer.getBeerId();

        // 2. Load two separate "snapshots" of the same entity (mimicking two threads/users)
        Beer beerInstance1 = beerRepository.findById(beerId).get();
        // copy to simulate separate instance
        Beer beerInstance2 = Beer.builder()
                .beerId(beerInstance1.getBeerId())
                .beerName(beerInstance1.getBeerName())
                .beerStyle(beerInstance1.getBeerStyle())
                .upc(beerInstance1.getUpc())
                .quantityOnHand(beerInstance1.getQuantityOnHand())
                .price(beerInstance1.getPrice())
                .externalBeerId(beerInstance1.getExternalBeerId())
                .createdDate(beerInstance1.getCreatedDate())
                .lastModifiedDate(beerInstance1.getLastModifiedDate())
                .version(beerInstance1.getVersion())
                .build();

        // 3. User 1 updates the beer (Version increments from 0 to 1)
        beerInstance1.setBeerName("Updated by User 1");
        beerRepository.saveAndFlush(beerInstance1);

        // 4. User 2 tries to update using their STALE snapshot (still has Version 0)
        beerInstance2.setBeerName("Updated by User 2");

        // Use Spring's ObjectOptimisticLockingFailureException or JPA's OptimisticLockException
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            beerRepository.saveAndFlush(beerInstance2);
        });
    }
    @Test
    void givenConcurrentUpdates_whenSave_thenThrowOptimisticLockException2() {
        // 1. Initial setup
        Beer beer = Beer.builder()
                .beerName("Concurrent Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("LOCK_TEST_UPC")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .externalBeerId(999999)
                .build();

        Beer savedBeer = beerRepository.saveAndFlush(beer);
        UUID beerId = savedBeer.getBeerId();

        // 2. Load the first "snapshot" and then CLEAR the session
        Beer beerInstance1 = beerRepository.findById(beerId).get();
        entityManager.detach(beerInstance1); // Manually detach to simulate a separate session

        // 3. Load the second "snapshot"
        Beer beerInstance2 = beerRepository.findById(beerId).get();
        entityManager.detach(beerInstance2); // Now you have two distinct objects with Version 0

        // 4. User 1 updates successfully (Version goes 0 -> 1 in DB)
        beerInstance1.setBeerName("Updated by User 1");
        beerRepository.saveAndFlush(beerInstance1);

        // 5. User 2 tries to update with Version 0
        beerInstance2.setBeerName("Updated by User 2");

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            beerRepository.saveAndFlush(beerInstance2);
        });
    }
}