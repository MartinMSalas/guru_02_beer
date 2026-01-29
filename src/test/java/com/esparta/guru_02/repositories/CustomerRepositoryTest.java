package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/*
 * Author: m
 * Date: 29/1/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    static Customer customer;
    static List<Customer> customerList;

    @BeforeAll
    static void setUp() {
        customer = Customer.builder()
                .customerName("Panchito")
                .build();
        customerList = List.of(customer);
    }

    @Test
    void givenValidCustomer_whenSavingCustomer_thenReturnsBeerSaved(){
        // GIVEN

        // WHEN
        Customer savedCustomer = customerRepository.save(customer);

        // THEN
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerId()).isNotNull();

    }
}
