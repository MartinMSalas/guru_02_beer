package com.esparta.guru_02.controllers;

import com.esparta.guru_02.exceptions.BadRequestException;
import com.esparta.guru_02.model.CustomerDTO;
import com.esparta.guru_02.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@AllArgsConstructor
@RestController
@RequestMapping(CustomerController.CUSTOMER_PATH)
@Slf4j
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = "/{customerId}";

    private static final String HEADER_TOTAL_COUNT = "X-Total-Count";

    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("In CustomerController.createNewCustomer() with customerDTO: {}", customerDTO);
        CustomerDTO savedCustomerDTO = customerService.saveNewCustomer(customerDTO);
        log.debug("Saved CustomerDTO: {}", savedCustomerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomerDTO.getCustomerId().toString());

        return new ResponseEntity<>(savedCustomerDTO, headers, org.springframework.http.HttpStatus.CREATED);
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID customerId){
        log.debug("In CustomerController.getCustomerById() with id: {}", customerId);
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        HttpHeaders headers = new HttpHeaders();

        headers.add("location", "/api/v1/customer/" + customerDTO.getCustomerId().toString());
        return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
    }

    /**
     * Returns all customers.
     *
     * Contract:
     * - 200 OK with list (possibly empty)
     * - Never returns null
     */
    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer size
            // @RequestParam(required = false) Integer size
    ) {

        log.debug("GET /api/v1/customers?page={}, size={}", page, size);

        if ((page < 0) || ( size <= 0) || size > 100) {
            throw new BadRequestException("Invalid pagination parameters");
        }

        // For now, ignoring pagination parameters
        List<CustomerDTO> customers = customerService.getAllCustomers();
        // Add X-Total-Count header if pagination is implemented in the future
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().mustRevalidate());
        headers.add(HEADER_TOTAL_COUNT, String.valueOf(customers.size()));
        return new ResponseEntity<>(customers, headers, HttpStatus.OK);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customerDTO) {
        log.debug("In CustomerController.updateCustomer() with id: {}", customerId);
        CustomerDTO customerDTOUpdated = customerService.updateCustomer(customerId, customerDTO);
        log.debug("Updated CustomerDTO: {}", customerDTOUpdated);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerDTOUpdated.getCustomerId().toString());
        return new ResponseEntity<>(customerDTOUpdated, headers, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable UUID customerId) {
        log.debug("In CustomerController.deleteCustomer() with id: {}", customerId);

        CustomerDTO customerDTODeleted = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(customerDTODeleted, HttpStatus.OK);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customerDTO) {
        log.debug("In CustomerController.patchCustomer() with id: {}", customerId);
        CustomerDTO customerDTOPatched = customerService.patchCustomer(customerId, customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerDTOPatched.getCustomerId().toString());
        return new ResponseEntity<>(customerDTOPatched, headers, HttpStatus.OK);
    }
}
