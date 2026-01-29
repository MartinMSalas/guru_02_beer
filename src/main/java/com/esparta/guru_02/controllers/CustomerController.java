package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.CustomerDTO;
import com.esparta.guru_02.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID customerId){
        log.debug("In CustomerController.getCustomerById() with id: {}", customerId);
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/api/v1/customer/" + customerDTO.getCustomerId().toString());
        return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getAllCustomers(){
        log.debug("In CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("In CustomerController.createNewCustomer() with customerDTO: {}", customerDTO);
        CustomerDTO savedCustomerDTO = customerService.saveNewCustomer(customerDTO);
        log.debug("Saved CustomerDTO: {}", savedCustomerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomerDTO.getCustomerId().toString());

        return new ResponseEntity<>(savedCustomerDTO, headers, org.springframework.http.HttpStatus.CREATED);
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
