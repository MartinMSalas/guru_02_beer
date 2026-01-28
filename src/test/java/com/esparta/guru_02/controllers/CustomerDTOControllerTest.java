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
import java.util.Optional;
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
class CustomerDTOControllerTest {


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
                .id(UUID.randomUUID())
                .customerName("PoNCio")
                .build();
        customersList = List.of(customerDTO);
    }


    @Test
    void givenValidCustomerId_whenDeleteCustomer_thenReturn200Ok() throws Exception {
        // given
        UUID customerId = customerDTO.getId();
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
    void givenValidCustomerPayload_whenPostCustomer_thenReturn201Created() throws Exception {
        // given
        UUID customerId = customerDTO.getId();
        given(customerService.saveNewCustomer(customerDTO)).willReturn(customerDTO);

        // when & then
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customerDTO/" + customerId.toString()));
    }

    @Test
    void givenValidUpdatedCustomerPayload_whenUpdateCustomer_thenReturn200Ok() throws Exception {
        // given
        UUID customerId = customerDTO.getId();
        given(customerService.updateCustomer(eq(customerDTO.getId()), any(CustomerDTO.class))).willReturn(customerDTO);

        // when & then
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/customerDTO/" + customerId.toString()));

        //verify(customerService, times(1)).updateCustomer(any(UUID.class), any(CustomerDTO.class));
        verify(customerService).updateCustomer(eq(customerId), any(CustomerDTO.class));
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
    void  givenValidCustomerId_whenGetCustomerById_thenReturn200OkAndCustomer() throws Exception {
        UUID customerId = customerDTO.getId();
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(
                Optional.of(customerDTO)
        );
        //System.out.println(beerDTO.getId());
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,  customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(customerId.toString())))
                .andExpect(jsonPath("$.customerName", is("PoNCio")));

    }

    @Test
    void givenNonExistingCustomerId_whenGetCustomerById_thenReturn404NotFound() throws Exception {

        // given
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        // when
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());

        // then
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