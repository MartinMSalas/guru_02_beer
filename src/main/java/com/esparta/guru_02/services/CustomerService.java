package com.esparta.guru_02.services;

import com.esparta.guru_02.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface CustomerService {

    CustomerDTO getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(UUID customerId, CustomerDTO customerDTO);

    CustomerDTO deleteCustomer(UUID customerId);

    CustomerDTO patchCustomer(UUID customerId, CustomerDTO customerDTO);
}
