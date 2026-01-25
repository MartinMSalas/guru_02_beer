package com.esparta.guru_02.services;

import com.esparta.guru_02.model.Customer;

import java.util.List;
import java.util.UUID;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    Customer updateCustomer(UUID customerId, Customer customer);

    Customer deleteCustomer(UUID customerId);

    Customer patchCustomer(UUID customerId, Customer customer);
}
