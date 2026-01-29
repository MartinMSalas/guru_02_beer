package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;

import com.esparta.guru_02.model.CustomerDTO;

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

    static CustomerDTO customerDTO;
    static List<CustomerDTO> customersList;

    @BeforeAll
    static void setUp() {
        customerDTO = CustomerDTO.builder()
                .customerId(UUID.randomUUID())
                .customerName("PoNCio")
                .build();
        customersList = List.of(customerDTO);
    }

    @Test
    void givenValidCustomerPayload_whenPostCustomer_thenReturn201Created() throws Exception {
        // GIVEN a valid customer payload
        UUID customerId = customerDTO.getCustomerId();
        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(customerDTO);

        // WHEN the POST endpoint is called
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                // THEN the response status is 201 CREATED

                .andExpect(status().isCreated())
                // AND the Location header points to the new resource

                .andExpect(header().string("Location", "/api/v1/customer/" + customerId.toString()));
    }

    @Test
    void  givenValidCustomerId_whenGetCustomerById_thenReturn200OkAndCustomer() throws Exception {
        // GIVEN valid customer ID
        UUID customerId = customerDTO.getCustomerId();
        // AND the service returns the customerDTO


        given(customerService.getCustomerById(eq(customerId))).willReturn(customerDTO);

        // WHEN the GET-by-ID endpoint is called
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN the response status is 200 OK

                .andExpect(status().isOk())
                // AND the response is JSON

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // AND the returned beer matches the expected values

                .andExpect(jsonPath("$.customerId",is(customerId.toString())))
                .andExpect(jsonPath("$.customerName", is("PoNCio")));

    }

    @Test
    void testGetCustomersList() throws Exception {

        // GIVEN a list of customers returned by the service
        given(customerService.getAllCustomers()).willReturn(customersList);
        // WHEN the GET collection endpoint is called
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN the response status is 200 OK
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void givenNonExistingCustomerId_whenGetCustomerById_thenReturn404NotFound() throws Exception {

        // GIVEN the service throws NotFoundException
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        // WJHEN the GET-by-ID endpoint is called
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                // THEN the response status is 404 Not Found
                .andExpect(status().isNotFound());


    }

    @Test
    void givenValidUpdatedCustomerPayload_whenUpdateCustomer_thenReturn200OkAndCustomer() throws Exception {
        // GIVEN a valid updated customer payload
        UUID customerId = customerDTO.getCustomerId();
        given(customerService.updateCustomer(eq(customerDTO.getCustomerId()), any(CustomerDTO.class))).willReturn(customerDTO);

        // WHEN the PUT endpoint is called

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                // THEN the response status is 200 OK
                .andExpect(status().isOk())
                // AND the Location header is returned
                .andExpect(header().string("Location", "/api/v1/customer/" + customerId.toString()));

        //verify(customerService, times(1)).updateCustomer(any(UUID.class), any(CustomerDTO.class));
        // OR use ArgumentCaptor
        verify(customerService).updateCustomer(eq(customerId), any(CustomerDTO.class));
    }

    @Test
    void givenValidPatchCustomerPayload_whenPatchCustomer_thenReturn200Ok() throws Exception {
        // GIVEN a valid patched customer payload
        UUID customerId = customerDTO.getCustomerId();
        CustomerDTO patchedCustomer = CustomerDTO.builder()
                .customerName("PatchedName")
                .build();
        given(customerService.patchCustomer(eq(customerId), any(CustomerDTO.class))).willReturn(customerDTO);

        // WHEN the PATCH endpoint is called
        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchedCustomer)))
                // THEN the response status is 200 OK
                .andExpect(status().isOk());

        // Verify that the service method was called with correct parameters
        verify(customerService).patchCustomer(eq(customerId), any(CustomerDTO.class));
    }

    @Test
    void givenValidCustomerId_whenDeleteCustomer_thenReturn200Ok() throws Exception {
        // given
        UUID customerId = customerDTO.getCustomerId();
        given(customerService.deleteCustomer(eq(customerId))).willReturn(customerDTO);


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