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
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAllCustomers(){
        log.debug("In CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId){
        log.debug("In CustomerController.getCustomerById() with id: {}", customerId);
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("")
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer){
        log.debug("In CustomerController.createNewCustomer() with customer: {}", customer);
        Customer savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(savedCustomer, headers, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        log.debug("In CustomerController.updateCustomer() with id: {}", customerId);
        Customer customerUpdated = customerService.updateCustomer(customerId, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerUpdated.getId().toString());
        return new ResponseEntity<>(customerUpdated, headers, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable UUID customerId) {
        log.debug("In CustomerController.deleteCustomer() with id: {}", customerId);

        Customer customerDeleted = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(customerDeleted, HttpStatus.OK);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> patchCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        log.debug("In CustomerController.patchCustomer() with id: {}", customerId);
        Customer customerPatched = customerService.patchCustomer(customerId, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerPatched.getId().toString());
        return new ResponseEntity<>(customerPatched, headers, HttpStatus.OK);
    }
}
