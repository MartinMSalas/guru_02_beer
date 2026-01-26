package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;

import com.esparta.guru_02.model.Customer;

import com.esparta.guru_02.services.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers = BeerController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JpaAuditingConfig.class
        )
)
class CustomerControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    static Customer customer;
    static List<Customer> customerList;

    @BeforeAll
    static void setUp() {
        customer = Customer.builder()
                .customerName("PoNCio")
                .build();
        customerList = List.of(customer);
    }


    @Test
    void testListCustomer() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerList);

        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void getBeerById() throws Exception {
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(
                customer
        );
        //System.out.println(beer.getId());
        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(customer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is("PoNCio")));

    }
    @Test
    void getAllCustomers() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void createNewCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void patchCustomer() {
    }
}