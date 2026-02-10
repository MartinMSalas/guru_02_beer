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
import org.springframework.data.domain.Page;
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
        ResponseEntity<Page<BeerDTO>> response =
                beerController.getAllBeers(null,null,0, 25);

        assertThat(response.getBody())
                .as("Expected at least one Beer to exist for this test")
                .isNotNull()
                .isNotEmpty();

        return response.getBody().getContent().getFirst();
    }

    /* ==========================================================
       Tests
       ========================================================== */

    /**
     * Post Methods
     */
    /*
     * Test the "create new beer" happy path.
     * Payload is valid, so we expect the beer to be created successfully.
     * Parameters:
     * - beerName: "New Beer"
     * - beerStyle: AMERICAN_BARLEYWINE
     * - upc: "999999999999"
     * - price: 9.99
     * - quantityOnHand: 100
     *
     * Verifies:
     * - 201 Created status
     * - Location header is present
     * - Response body contains the created Beer with an ID
     */
    @Transactional
    @Rollback
    @Test
    void givenValidBeerDTOPayload_whenCreateNewBeer_thenReturn201AndBeerWithId() {

        // ===== GIVEN =====
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
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

    /**
     * Test the "get beer by ID" happy path.
     * A beer with the given ID exists, so we expect to retrieve it successfully.
     * Parameters:
     * - beerId: ID of an existing beer
     * Verifies:
     * - 200 OK status
     * - Response body contains the expected BeerDTO with the correct ID
     */
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

    /**
     * Test the "get beer by ID" error path.
     * No beer with the given ID exists, so we expect a NotFoundException to be thrown.
     * Parameters:
     * - beerId: Random UUID that does not exist in the database
     * Verifies:
     * - NotFoundException is thrown
     */
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

    /**
     * Test the "list beers" happy path.
     * Beers exist in the database, so we expect to retrieve a non-empty list.
     * Parameters:
     * - page: 0
     * - size: 25
     * Verifies:
     * - 200 OK status
     * - Response body contains a non-empty list of BeerDTOs
     */
    @Test
    void givenBeersExist_whenListBeers_thenReturn200AndNonEmptyList() {

        // ===== WHEN =====
        ResponseEntity<Page<BeerDTO>> response =
                beerController.getAllBeers(null,null,0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();

    }

    /**
     * Test the "list beers by name" happy path.
     * Beers with the given name exist in the database, so we expect to retrieve a non-empty list.
     * Parameters:
     * - beerName: "IPA"
     * - page: 0
     * - size: 25
     * Verifies:
     * - 200 OK status
     * - Response body contains a non-empty list of BeerDTOs
     */
    @Test
    void givenBeersExist_whenListBeersByName_thenReturn200AndNonEmptyList() {

        // ===== WHEN =====
        ResponseEntity<Page<BeerDTO>> response =
                beerController.getAllBeers("IPA",null, 0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();

    }


    /**
     * Test the "list beers" edge case.
     * No beers exist in the database, so we expect to retrieve an empty list.
     * Parameters:
     * - page: 0
     * - size: 25
     * Verifies:
     * - 200 OK status
     * - Response body contains an empty list (not null)
     */
    @Transactional
    @Rollback
    @Test
    void givenNoBeersExist_whenListBeers_thenReturnEmptyList() {

        // ===== GIVEN =====
        beerRepository.deleteAll();

        // ===== WHEN =====
        ResponseEntity<Page<BeerDTO>> response =
                beerController.getAllBeers(null,null,0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    /**
     * Test the "update beer" happy path.
     * A beer with the given ID exists, and the payload is valid, so we expect the beer to be updated successfully.
     * Parameters:
     * - beerId: ID of an existing beer
     * - payload: BeerDTO with updated fields (e.g., new beerName)
     * Verifies:
     * - 200 OK status
     * - Response body contains the updated BeerDTO with the changes applied
     */
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

    /**
     * Test the "update beer" error path.
     * No beer with the given ID exists, so we expect a NotFoundException to be thrown.
     * Parameters:
     * - beerId: Random UUID that does not exist in the database
     * - payload: BeerDTO with some fields (e.g., beerName)
     * Verifies:
     * - NotFoundException is thrown
     */
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

    /**
     * Test the "patch beer" happy path.
     * A beer with the given ID exists, and the payload contains valid fields to update, so we expect the beer to be patched successfully.
     * Parameters:
     * - beerId: ID of an existing beer
     * - payload: BeerDTO with only the fields to update (e.g., new beerName, other fields null)
     * Verifies:
     * - 200 OK status
     * - Response body contains the patched BeerDTO with only the specified changes applied
     */
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

    /**
     * Test the "patch beer" error path.
     * No beer with the given ID exists, so we expect a NotFoundException to be thrown.
     * Parameters:
     * - beerId: Random UUID that does not exist in the database
     * - payload: BeerDTO with some fields to patch (e.g., beerName)
     * Verifies:
     * - NotFoundException is thrown
     */
    @Transactional
    @Rollback
    @Test
    void givenNonExistingBeerId_whenPatchBeer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(beerRepository.existsById(invalidId)).isFalse();

        BeerDTO patchPayload = BeerDTO.builder()
                .beerName("Does Not Matter")
                .build();

        assertThrows(NotFoundException.class, () ->
                beerController.patchBeer(invalidId, patchPayload)
        );
    }


    /**
     * Test the "delete beer" happy path.
     * A beer with the given ID exists, so we expect it to be deleted successfully.
     * Parameters:
     * - beerId: ID of an existing beer
     * Verifies:
     * - 200 OK status
     * - Response body contains the deleted BeerDTO
     * - Beer is actually removed from the database (sanity check)
     */
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

    /**
     * Test the "delete beer" error path.
     * No beer with the given ID exists, so we expect a NotFoundException to be thrown.
     * Parameters:
     * - beerId: Random UUID that does not exist in the database
     * Verifies:
     * - NotFoundException is thrown
     */
    @Test
    void givenNonExistingBeerId_whenDeleteBeer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(beerRepository.existsById(invalidId)).isFalse();

        assertThrows(NotFoundException.class, () ->
                beerController.deleteBeer(invalidId)
        );
    }

    /**
     * Test the "list beers" error path.
     * Invalid pagination parameters are provided, so we expect a BadRequestException to be thrown.
     * Parameters:
     * - page: 0 (valid)
     * - size: 500 (invalid, exceeds maximum allowed)
     * Verifies:
     * - BadRequestException is thrown
     */
    @Test
    void givenInvalidPagination_whenListBeers_thenThrowBadRequestException() {

        // page OK, size invalid (>100)
        assertThrows(BadRequestException.class, () ->
                beerController.getAllBeers(null,null, 0, 500)
        );
    }
}