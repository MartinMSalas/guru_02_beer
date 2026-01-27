package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;
import com.esparta.guru_02.model.Beer;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.services.BeerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.notANumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// SpringBootTest will start the full application context
// @SpringBootTest
// WebMvcTest will start only the web layer, useful for controller tests

@WebMvcTest(
        controllers = BeerController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JpaAuditingConfig.class
        )
)
@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

// Autowire the BeerController to test its methods
//    @Autowired
//    BeerController beerController;

    // We need to mock the BeerService dependency for the BeerController
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    static Beer beer;
    static List<Beer> beerList;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeAll
    static void setUp() {
        beer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        beerList = List.of(beer);
    }

    @Test
    void givenValidBeerId_whenDeleteBeer_thenReturn200Ok() throws Exception {
        // given
        UUID beerId = beer.getId();
        given(beerService.deleteById(eq(beerId))).willReturn(beer);


        // when & then
        mockMvc.perform(delete("/api/v1/beer/" + beerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        UUID capturedUuid = uuidArgumentCaptor.getValue();
        // assert(capturedUuid.equals(beerId));
     //   assertEquals(beerId, capturedUuid);
        verify(beerService, times(1)).deleteById(beerId);
    }

    @Test
    void givenValidBeerPayload_whenPostBeer_thenReturn201Created() throws Exception {
        // given
        UUID beerId = beer.getId();
        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beer);

        // when & then
        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/beer/" + beerId.toString()));


    }

    @Test
    void givenValidUpdatedBeerPayload_whenUpdateBeer_thenReturn200Ok() throws Exception {
        // given
        //UUID beerId = UUID.randomUUID();
        UUID beerId = beer.getId();
        given(beerService.updateBeer(eq(beerId),any(Beer.class) )).willReturn(beer);

        // when & then
        mockMvc.perform(put("/api/v1/beer/" + beerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/beer/" + beerId.toString()));


        verify(beerService, times(1)).updateBeer(eq(beerId), any(Beer.class));
        //verify(beerService).updateBeer(any(UUID.class), any(Beer.class));
    }

    @Test
    void givenValidPatchBeerPayload_whenPatchBeer_thenReturn200Ok() throws Exception {
        // given
        UUID beerId = beer.getId();
        given(beerService.patchBeer(eq(beerId),any(Beer.class) )).willReturn(beer);

        // when & then
        mockMvc.perform(patch("/api/v1/beer/" + beerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/beer/" + beerId.toString()));

        verify(beerService).patchBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        UUID capturedUuid = uuidArgumentCaptor.getValue();
        Beer capturedBeer = beerArgumentCaptor.getValue();

        // assert(capturedUuid.equals(beerId));
        assertEquals(beerId, capturedUuid);
        assertEquals(beer, capturedBeer);
        verify(beerService, times(1)).patchBeer(eq(beerId), any(Beer.class));
        //verify(beerService).updateBeer(any(UUID.class), any(Beer.class));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerList);

        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void getBeerById() throws Exception {

        UUID beerId = beer.getId();
        when(beerService.getBeerById(any(UUID.class))).thenReturn(
                beer
        );

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(beerId.toString())))
                .andExpect(jsonPath("$.beerName", is("Galaxy Cat")));

    }

    @Test
    void testGetBeerById() {
    }

    @Test
    void createNewBeer() {
    }

    @Test
    void updateBeer() {
    }

    @Test
    void getBeer() {
    }

    @Test
    void deleteBeer() {
    }

    @Test
    void patchBeer() {
    }
}