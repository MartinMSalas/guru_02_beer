package com.esparta.guru_02.services;

import com.esparta.guru_02.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    Map<UUID, Customer> customerData;

    public CustomerServiceImpl() {
        customerData = new HashMap<>();
        Customer customer1 = Customer.builder().customerName("john").id(UUID.randomUUID()).build();

        Customer customer2 = Customer.builder().customerName("jane").id(UUID.randomUUID()).build();

        Customer customer3 = Customer.builder().customerName("smith").id(UUID.randomUUID()).build();


        customerData.put(customer1.getId(), customer1);
        customerData.put(customer2.getId(), customer2);
        customerData.put(customer3.getId(), customer3);

    }

    @Override
    public List<Customer> getAllCustomers() {

        log.debug("In CustomerServiceImpl.getAllCustomers()");
        return new ArrayList<>(customerData.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("In CustomerServiceImpl.getCustomerById() with id: {}", id);
        if (customerData.containsKey(id)) {
            return customerData.get(id);
        }
        return null;
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {
        log.debug("In CustomerServiceImpl.saveNewCustomer() with customer: {}", customer);
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .build();
        // Note: In a real application, you would save the new customer to a database or persistent storage.

        customerData.put(newCustomer.getId(), newCustomer); // This line is illustrative; Map.of creates an immutable map.
        return newCustomer;


    }

    @Override
    public Customer updateCustomer(UUID customerId, Customer customer) {
        log.debug("In CustomerServiceImpl.updateCustomer() with id: {}", customerId);
        if (customerData.containsKey(customerId)) {
            Customer updatedCustomer = Customer.builder()
                    .id(customerId)
                    .customerName(customer.getCustomerName())
                    .build();
            customerData.put(customerId, updatedCustomer);
            log.debug("Customer updated: {}", updatedCustomer);
            return updatedCustomer;
        }
        log.debug("Customer with id {} not found for update.", customerId);
        return null;
    }

    @Override
    public Customer deleteCustomer(UUID customerId) {
        log.debug("In CustomerServiceImpl.deleteCustomer() with id: {}", customerId);
        if (customerData.containsKey(customerId)) {
            Customer removedCustomer = customerData.remove(customerId);
            log.debug("Customer deleted: {}", removedCustomer);
            return removedCustomer;
        }
        log.debug("Customer with id {} not found for deletion.", customerId);
        return null;
    }

    @Override
    public Customer patchCustomer(UUID customerId, Customer customer) {
        log.debug("In CustomerServiceImpl.patchCustomer() with id: {}", customerId);
        Customer existingCustomer = customerData.get(customerId);
        if (existingCustomer != null) {
            if (customer.getCustomerName() != null) {
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            Customer savedCustomer = customerData.put(customerId, existingCustomer);
            log.debug("Customer patched: {}", existingCustomer);
            return existingCustomer;
        }
        log.debug("Customer with id {} not found for patching.", customerId);
        return null;

    }

}
