package com.esparta.guru_02.controllers;

import com.esparta.guru_02.exceptions.BadRequestException;
import com.esparta.guru_02.exceptions.NotFoundException;
import com.esparta.guru_02.model.CustomerDTO;
import com.esparta.guru_02.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Author: M
 * Date: 31-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@SpringBootTest
class CustomerControllerComponentTest {

    /*
     * Component test:
     * Controller → Service → Repository → Database
     * No HTTP, no mocks.
     */
    @Autowired
    CustomerController customerController;

    /*
     * Repository used ONLY for sanity checks.
     */
    @Autowired
    CustomerRepository customerRepository;

    @MockitoBean
    JwtDecoder jwtDecoder;

    /* ==========================================================
       Helper
       ========================================================== */

    private CustomerDTO anyExistingCustomer() {
        ResponseEntity<List<CustomerDTO>> response =
                customerController.getAllCustomers(0, 25);

        assertThat(response.getBody())
                .as("Expected at least one Customer to exist for this test")
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
    void givenValidCustomerPayload_whenCreateNewCustomer_thenReturn201AndCustomerWithId() {

        // ===== GIVEN =====
        CustomerDTO newCustomer = CustomerDTO.builder()
                .customerName("John Doe")
                .build();

        // ===== WHEN =====
        ResponseEntity<CustomerDTO> response =
                customerController.createNewCustomer(newCustomer);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        CustomerDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getCustomerId()).isNotNull();
        assertThat(body.getCustomerName()).isEqualTo("John Doe");
    }

    @Test
    void givenValidCustomerId_whenGetCustomerById_thenReturn200OkAndCustomer() {

        // ===== GIVEN =====
        CustomerDTO existing = anyExistingCustomer();

        // ===== WHEN =====
        ResponseEntity<CustomerDTO> response =
                customerController.getCustomerById(existing.getCustomerId());

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId())
                .isEqualTo(existing.getCustomerId());
    }

    @Test
    void givenNonExistingCustomerId_whenGetCustomerById_thenThrowNotFoundException() {

        // ===== GIVEN =====
        UUID invalidId = UUID.randomUUID();
        assertThat(customerRepository.existsById(invalidId)).isFalse();

        // ===== WHEN / THEN =====
        assertThrows(NotFoundException.class, () ->
                customerController.getCustomerById(invalidId)
        );
    }

    @Test
    void givenCustomersExist_whenListCustomers_thenReturn200AndNonEmptyList() {

        // ===== WHEN =====
        ResponseEntity<List<CustomerDTO>> response =
                customerController.getAllCustomers(0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void givenNoCustomersExist_whenListCustomers_thenReturnEmptyList() {

        // ===== GIVEN =====
        customerRepository.deleteAll();

        // ===== WHEN =====
        ResponseEntity<List<CustomerDTO>> response =
                customerController.getAllCustomers(0, 25);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void givenValidCustomerIdAndPayload_whenUpdateCustomer_thenReturn200AndUpdatedCustomer() {

        // ===== GIVEN =====
        CustomerDTO existing = anyExistingCustomer();

        CustomerDTO updatedPayload = CustomerDTO.builder()
                .customerName("Updated Customer")
                .build();

        // ===== WHEN =====
        ResponseEntity<CustomerDTO> response =
                customerController.updateCustomer(existing.getCustomerId(), updatedPayload);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerName())
                .isEqualTo("Updated Customer");
    }

    @Test
    void givenNonExistingCustomerId_whenUpdateCustomer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(customerRepository.existsById(invalidId)).isFalse();

        CustomerDTO payload = CustomerDTO.builder()
                .customerName("Does Not Matter")
                .build();

        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomer(invalidId, payload)
        );
    }

    @Transactional
    @Rollback
    @Test
    void givenValidCustomerIdAndPatchPayload_whenPatchCustomer_thenReturn200AndPatchedCustomer() {

        // ===== GIVEN =====
        CustomerDTO existing = anyExistingCustomer();

        CustomerDTO patchPayload = CustomerDTO.builder()
                .customerName("Patched Customer")
                .build();

        // ===== WHEN =====
        ResponseEntity<CustomerDTO> response =
                customerController.patchCustomer(existing.getCustomerId(), patchPayload);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerName())
                .isEqualTo("Patched Customer");
    }

    @Transactional
    @Rollback
    @Test
    void givenValidCustomerId_whenDeleteCustomer_thenReturn200AndCustomerIsRemoved() {

        // ===== GIVEN =====
        CustomerDTO existing = anyExistingCustomer();
        UUID customerId = existing.getCustomerId();

        // ===== WHEN =====
        ResponseEntity<CustomerDTO> response =
                customerController.deleteCustomer(customerId);

        // ===== THEN =====
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(customerRepository.existsById(customerId)).isFalse();
    }

    @Test
    void givenNonExistingCustomerId_whenDeleteCustomer_thenThrowNotFoundException() {

        UUID invalidId = UUID.randomUUID();
        assertThat(customerRepository.existsById(invalidId)).isFalse();

        assertThrows(NotFoundException.class, () ->
                customerController.deleteCustomer(invalidId)
        );
    }

    @Test
    void givenInvalidPagination_whenListCustomers_thenThrowBadRequestException() {

        // page OK, size invalid (>100)
        assertThrows(BadRequestException.class, () ->
                customerController.getAllCustomers(0, 500)
        );
    }
}