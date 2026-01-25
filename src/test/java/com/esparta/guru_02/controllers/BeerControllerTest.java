package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.Beer;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.services.BeerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;

import java.util.UUID;




import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// SpringBootTest will start the full application context
// @SpringBootTest
// WebMvcTest will start only the web layer, useful for controller tests
@WebMvcTest(BeerController.class)
class BeerControllerTest {

// Autowire the BeerController to test its methods
//    @Autowired
//    BeerController beerController;

    // We need to mock the BeerService dependency for the BeerController
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerService beerService;

    static Beer beer;

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
    }


    @Test
    void getBeerById() throws Exception {
        when(beerService.getBeerById(any(UUID.class))).thenReturn(
                beer
        );

        mockMvc.perform(get("api/v1/beer" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) ;
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
    void listBeers() {
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