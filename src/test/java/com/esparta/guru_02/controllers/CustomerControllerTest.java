package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;

import com.esparta.guru_02.model.Customer;

import com.esparta.guru_02.services.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// ----


// ---
@WebMvcTest(
        controllers = CustomerController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JpaAuditingConfig.class
        )
)
class CustomerControllerTest {


    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    static Customer customer;
    static List<Customer> customersList;

    @BeforeAll
    static void setUp() {
        customer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PoNCio")
                .build();
        customersList = List.of(customer);
    }


    @Test
    void givenValidCustomerId_whenDeleteCustomer_thenReturn200Ok() throws Exception {
        // given
        UUID customerId = customer.getId();
        given(customerService.deleteCustomer(eq(customerId))).willReturn(customer);


        // when & then
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());
        UUID capturedUuid = uuidArgumentCaptor.getValue();
        // assert(capturedUuid.equals(customerId));
        assertEquals(customerId, capturedUuid);
        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void givenValidCustomerPayload_whenPostCustomer_thenReturn201Created() throws Exception {
        // given
        UUID customerId = customer.getId();
        given(customerService.saveNewCustomer(customer)).willReturn(customer);

        // when & then
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customer/" + customerId.toString()));
    }

    @Test
    void givenValidUpdatedCustomerPayload_whenUpdateCustomer_thenReturn200Ok() throws Exception {
        // given
        UUID customerId = customer.getId();
        given(customerService.updateCustomer(eq(customer.getId()), any(Customer.class))).willReturn(customer);

        // when & then
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/customer/" + customerId.toString()));

        //verify(customerService, times(1)).updateCustomer(any(UUID.class), any(Customer.class));
        verify(customerService).updateCustomer(eq(customerId), any(Customer.class));
    }
    @Test
    void testGetCustomersList() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customersList);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void testGetCustomerById() throws Exception {
        UUID customerId = customer.getId();
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(
                customer
        );
        //System.out.println(beer.getId());
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(customerId.toString())))
                .andExpect(jsonPath("$.customerName", is("PoNCio")));

    }
    @Test
    void getAllCustomers() {
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