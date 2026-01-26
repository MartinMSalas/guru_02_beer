package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;
import com.esparta.guru_02.model.Beer;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.services.BeerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;




import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
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
    static List<Beer> beerList;

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
        when(beerService.getBeerById(any(UUID.class))).thenReturn(
                beer
        );
        System.out.println(beer.getId());
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(beer.getId().toString())))
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