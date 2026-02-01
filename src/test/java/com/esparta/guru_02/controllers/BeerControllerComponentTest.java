package com.esparta.guru_02.controllers;

import com.esparta.guru_02.exceptions.BadRequestException;
import com.esparta.guru_02.exceptions.NotFoundException;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.repositories.BeerRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerComponentTest {

    /*
     * Component test:
     * Controller → Service → Repository → Database
     * No HTTP, no mocks.
     */
    @Autowired
    BeerController beerController;

    /*
     * Repository used ONLY for sanity checks.
     */
    @Autowired
    BeerRepository beerRepository;

    /* ==========================================================
       Helper
       ========================================================== */

    private BeerDTO anyExistingBeer() {
        ResponseEntity<List<BeerDTO>> response =
                beerController.getAllBeers(0, 25);

        assertThat(response.getBody())
                .as("Expected at least one Beer to exist for this test")
                .isNotNull()
                .isNotEmpty();

        return response.getBody().getFirst();
    }

    /* ==========================================================
       Tests
       ========================================================== */

    @Transactional
    @Rollback
    @Test
    void givenValidBeerDTOPayload_whenCreateNewBeer_thenReturn201AndBeerWithId() {

        // ===== GIVEN =====
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.IPA)
                .upc("999999999999")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(100)
                .build();

        // ===== WHEN =====
        ResponseEntity<BeerDTO> response =
                beerController.createNewBeer(newBeer);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        BeerDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getBeerId()).isNotNull();
        assertThat(body.getBeerName()).isEqualTo("New Beer");
    }

    @Test
    void givenValidBeerId_whenGetBeerById_thenReturn200OkAndBeer() {

        // ===== GIVEN =====
        BeerDTO existing = anyExistingBeer();

        // ===== WHEN =====
        ResponseEntity<BeerDTO> response =
                beerController.getBeerById(existing.getBeerId());

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBeerId())
                .isEqualTo(existing.getBeerId());
    }

    @Test
    void givenNonExistingBeerId_whenGetBeerById_thenThrowNotFoundException() {

        // ===== GIVEN =====
        UUID invalidId = UUID.randomUUID();

        // Sanity check: ensure DB really does NOT contain it
        assertThat(beerRepository.existsById(invalidId)).isFalse();

        // ===== WHEN / THEN =====
        assertThrows(NotFoundException.class, () ->
                beerController.getBeerById(invalidId)
        );
    }

    @Test
    void givenBeersExist_whenListBeers_thenReturn200AndNonEmptyList() {

        // ===== WHEN =====
        ResponseEntity<List<BeerDTO>> response =
                beerController.getAllBeers(0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void givenNoBeersExist_whenListBeers_thenReturnEmptyList() {

        // ===== GIVEN =====
        beerRepository.deleteAll();

        // ===== WHEN =====
        ResponseEntity<List<BeerDTO>> response =
                beerController.getAllBeers(0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void givenValidBeerIdAndPayload_whenUpdateBeer_thenReturn200AndUpdatedBeer() {

        // ===== GIVEN =====
        BeerDTO existing = anyExistingBeer();

        BeerDTO updatedPayload = BeerDTO.builder()
                .beerName("Updated Beer")
                .beerStyle(existing.getBeerStyle())
                .upc(existing.getUpc())
                .price(existing.getPrice())
                .quantityOnHand(existing.getQuantityOnHand())
                .build();

        // ===== WHEN =====
        ResponseEntity<BeerDTO> response =
                beerController.updateBeer(existing.getBeerId(), updatedPayload);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void givenNonExistingBeerId_whenUpdateBeer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(beerRepository.existsById(invalidId)).isFalse();

        BeerDTO payload = BeerDTO.builder()
                .beerName("Does Not Matter")
                .build();

        assertThrows(NotFoundException.class, () ->
                beerController.updateBeer(invalidId, payload)
        );
    }

    @Transactional
    @Rollback
    @Test
    void givenValidBeerIdAndPatchPayload_whenPatchBeer_thenReturn200AndPatchedBeer() {

        // ===== GIVEN =====
        BeerDTO existing = anyExistingBeer();

        BeerDTO patchPayload = BeerDTO.builder()
                .beerName("Patched Beer")
                .build();

        // ===== WHEN =====
        ResponseEntity<BeerDTO> response =
                beerController.patchBeer(existing.getBeerId(), patchPayload);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBeerName()).isEqualTo("Patched Beer");
    }

    @Transactional
    @Rollback
    @Test
    void givenValidBeerId_whenDeleteBeer_thenReturn200AndBeerIsRemoved() {

        // ===== GIVEN =====
        BeerDTO existing = anyExistingBeer();
        UUID beerId = existing.getBeerId();

        // ===== WHEN =====
        ResponseEntity<BeerDTO> response =
                beerController.deleteBeer(beerId);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beerRepository.existsById(beerId)).isFalse();
    }

    @Test
    void givenNonExistingBeerId_whenDeleteBeer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(beerRepository.existsById(invalidId)).isFalse();

        assertThrows(NotFoundException.class, () ->
                beerController.deleteBeer(invalidId)
        );
    }

    @Test
    void givenInvalidPagination_whenListBeers_thenThrowBadRequestException() {

        // page OK, size invalid (>100)
        assertThrows(BadRequestException.class, () ->
                beerController.getAllBeers(0, 500)
        );
    }
}