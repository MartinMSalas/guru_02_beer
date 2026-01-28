package com.esparta.guru_02.services;

import com.esparta.guru_02.model.CustomerDTO;
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
    Map<UUID, CustomerDTO> customerData;

    public CustomerServiceImpl() {
        customerData = new HashMap<>();
        CustomerDTO customerDTO1 = CustomerDTO.builder().customerName("john").id(UUID.randomUUID()).build();

        CustomerDTO customerDTO2 = CustomerDTO.builder().customerName("jane").id(UUID.randomUUID()).build();

        CustomerDTO customerDTO3 = CustomerDTO.builder().customerName("smith").id(UUID.randomUUID()).build();


        customerData.put(customerDTO1.getId(), customerDTO1);
        customerData.put(customerDTO2.getId(), customerDTO2);
        customerData.put(customerDTO3.getId(), customerDTO3);

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        log.debug("In CustomerServiceImpl.getAllCustomers()");
        return new ArrayList<>(customerData.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("In CustomerServiceImpl.getCustomerById() with id: {}", id);
        if (customerData.containsKey(id)) {
            return Optional.of(customerData.get(id));
        }
        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl.saveNewCustomer() with customerDTO: {}", customerDTO);
        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customerDTO.getCustomerName())
                .build();
        // Note: In a real application, you would save the new customerDTO to a database or persistent storage.

        customerData.put(newCustomerDTO.getId(), newCustomerDTO); // This line is illustrative; Map.of creates an immutable map.
        return newCustomerDTO;


    }

    @Override
    public CustomerDTO updateCustomer(UUID customerId, CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl.updateCustomer() with id: {}", customerId);
        if (customerData.containsKey(customerId)) {
            CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                    .id(customerId)
                    .customerName(customerDTO.getCustomerName())
                    .build();
            customerData.put(customerId, updatedCustomerDTO);
            log.debug("CustomerDTO updated: {}", updatedCustomerDTO);
            return updatedCustomerDTO;
        }
        log.debug("CustomerDTO with id {} not found for update.", customerId);
        return null;
    }

    @Override
    public CustomerDTO deleteCustomer(UUID customerId) {
        log.debug("In CustomerServiceImpl.deleteCustomer() with id: {}", customerId);
        if (customerData.containsKey(customerId)) {
            CustomerDTO removedCustomerDTO = customerData.remove(customerId);
            log.debug("CustomerDTO deleted: {}", removedCustomerDTO);
            return removedCustomerDTO;
        }
        log.debug("CustomerDTO with id {} not found for deletion.", customerId);
        return null;
    }

    @Override
    public CustomerDTO patchCustomer(UUID customerId, CustomerDTO customerDTO) {
        log.debug("In CustomerServiceImpl.patchCustomer() with id: {}", customerId);
        CustomerDTO existingCustomerDTO = customerData.get(customerId);
        if (existingCustomerDTO != null) {
            if (customerDTO.getCustomerName() != null) {
                existingCustomerDTO.setCustomerName(customerDTO.getCustomerName());
            }
            CustomerDTO savedCustomerDTO = customerData.put(customerId, existingCustomerDTO);
            log.debug("CustomerDTO patched: {}", existingCustomerDTO);
            return existingCustomerDTO;
        }
        log.debug("CustomerDTO with id {} not found for patching.", customerId);
        return null;

    }

}
