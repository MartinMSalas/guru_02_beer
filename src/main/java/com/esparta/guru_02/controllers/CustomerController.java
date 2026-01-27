package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.Customer;
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

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers(){
        log.debug("In CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable UUID customerId){
        log.debug("In CustomerController.getCustomerById() with id: {}", customerId);
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer){
        log.debug("In CustomerController.createNewCustomer() with customer: {}", customer);
        Customer savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(savedCustomer, headers, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        log.debug("In CustomerController.updateCustomer() with id: {}", customerId);
        Customer customerUpdated = customerService.updateCustomer(customerId, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerUpdated.getId().toString());
        return new ResponseEntity<>(customerUpdated, headers, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable UUID customerId) {
        log.debug("In CustomerController.deleteCustomer() with id: {}", customerId);

        Customer customerDeleted = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(customerDeleted, HttpStatus.OK);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> patchCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        log.debug("In CustomerController.patchCustomer() with id: {}", customerId);
        Customer customerPatched = customerService.patchCustomer(customerId, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerPatched.getId().toString());
        return new ResponseEntity<>(customerPatched, headers, HttpStatus.OK);
    }
}
