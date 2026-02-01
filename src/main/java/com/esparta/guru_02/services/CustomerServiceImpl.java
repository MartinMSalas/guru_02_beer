package com.esparta.guru_02.services;


import com.esparta.guru_02.entities.Customer;
import com.esparta.guru_02.exceptions.NotFoundException;
import com.esparta.guru_02.mappers.CustomerMapper;

import com.esparta.guru_02.model.CustomerDTO;
import com.esparta.guru_02.repositories.CustomerRepository;

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
    //Map<UUID, CustomerDTO> customerData;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        log.debug("In CustomerServiceImpl getAllCustomers()");

        return customerMapper.customerListToCustomerDTOList(customerRepository.findAll());
    }

    @Override
    public CustomerDTO getCustomerById(UUID customerId) {
        log.debug("In CustomerServiceImpl getCustomerById() with customerId: {}", customerId);
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must be provided");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(
                        "Customer not found with id " + customerId
                ));

        log.debug("Loaded Customer: {}", customer);

        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl saveNewCustomer() with customerDTO: {}", customerDTO);
        Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customerToSave);
        log.debug("Customer entity saved: {}", savedCustomer);

        return customerMapper.customerToCustomerDTO(savedCustomer);


    }

    @Override
    public CustomerDTO updateCustomer(UUID customerId, CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl  updateCustomer() with customerId: {}", customerId);
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must be provided for update");
        }
        Customer customerLoaded = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(
                        "Customer not found with id " + customerId
                ));
        log.debug("Loaded Customer entity for update: {}", customerLoaded);
        //  Apply DTO values to the managed entity
        //     - does NOT touch id
        //     - does NOT touch auditing fields
        //     - does NOT touch version
        customerMapper.updateCustomerFromDTO(customerDTO, customerLoaded);
        //  Explicit save (optional but clear)
        Customer customerSaved = customerRepository.save(customerLoaded);
        log.debug("Customer entity updated: {}", customerSaved);
        //  Return fresh DTO (with updated auditing + version)

        return customerMapper.customerToCustomerDTO(customerSaved);
    }

    @Override
    public CustomerDTO deleteCustomer(UUID customerId) {
        log.debug("In CustomerServiceImpl deleteCustomer() with customerId: {}", customerId);

        if(customerId == null){
            throw new IllegalArgumentException("Customer ID must be provided for deletion");
        }
        Customer customerToDelete = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(
                        "Customer not found with id " + customerId
                ));
        log.debug("Loaded Customer entity for deletion: {}", customerToDelete);
        customerRepository.delete(customerToDelete);
        return customerMapper.customerToCustomerDTO(customerToDelete);
    }

    @Override
    public CustomerDTO patchCustomer(UUID customerId, CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl patchCustomer() with customerId: {}", customerId);
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must be provided for patch");
        }
        Customer customerLoaded = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(
                        "Customer not found with id " + customerId
                ));
        log.debug("Loaded Customer entity for patching: {}", customerLoaded);
        //  Apply non-null DTO values to the managed entity
        //     - does NOT touch id
        //     - does NOT touch auditing fields
        //     - does NOT touch version
        customerMapper.patchCustomerFromDTO(customerDTO, customerLoaded);

        //  Explicit save (optional but clear)
        Customer customerSaved = customerRepository.save(customerLoaded);
        log.debug("Customer entity patched and saved: {}", customerSaved);
        //  Return fresh DTO (with updated auditing + version)
        return customerMapper.customerToCustomerDTO(customerSaved);
    }

}
