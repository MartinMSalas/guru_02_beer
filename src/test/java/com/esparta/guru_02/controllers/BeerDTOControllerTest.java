package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;
import com.esparta.guru_02.model.BeerDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
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
class BeerDTOControllerTest {

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

    static BeerDTO beerDTO;
    static List<BeerDTO> beerDTOList;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeAll
    static void setUp() {
        beerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        beerDTOList = List.of(beerDTO);
    }

    @Test
    void givenValidBeerId_whenDeleteBeer_thenReturn200Ok() throws Exception {
        // given
        UUID beerId = beerDTO.getId();
        given(beerService.deleteById(eq(beerId))).willReturn(beerDTO);


        // when & then
        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beerId)
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
        UUID beerId = beerDTO.getId();
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerDTO);

        // when & then
        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/beerDTO/" + beerId.toString()));


    }

    @Test
    void givenValidUpdatedBeerPayload_whenUpdateBeer_thenReturn200Ok() throws Exception {
        // given
        //UUID beerId = UUID.randomUUID();
        UUID beerId = beerDTO.getId();
        given(beerService.updateBeer(eq(beerId),any(BeerDTO.class) )).willReturn(beerDTO);

        // when & then
        mockMvc.perform(put(BeerController.BEER_PATH_ID, beerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/beerDTO/" + beerId.toString()));


        verify(beerService, times(1)).updateBeer(eq(beerId), any(BeerDTO.class));
        //verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void givenValidPatchBeerPayload_whenPatchBeer_thenReturn200Ok() throws Exception {
        // given
        UUID beerId = beerDTO.getId();
        given(beerService.patchBeer(eq(beerId),any(BeerDTO.class) )).willReturn(beerDTO);

        // when & then
        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/v1/beerDTO/" + beerId.toString()));

        verify(beerService).patchBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        UUID capturedUuid = uuidArgumentCaptor.getValue();
        BeerDTO capturedBeerDTO = beerArgumentCaptor.getValue();

        // assert(capturedUuid.equals(beerId));
        assertEquals(beerId, capturedUuid);
        assertEquals(beerDTO, capturedBeerDTO);
        verify(beerService, times(1)).patchBeer(eq(beerId), any(BeerDTO.class));
        //verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerDTOList);

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void givenValidBeerId_whenGetBeerById_thenReturn200OkAndBeerJson() throws Exception {

        UUID beerId = beerDTO.getId();
        when(beerService.getBeerById(any(UUID.class))).thenReturn(
                Optional.of(beerDTO)
        );

        mockMvc.perform(get(BeerController.BEER_PATH_ID, beerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(beerId.toString())))
                .andExpect(jsonPath("$.beerName", is("Galaxy Cat")));

    }

    @Test
    void givenNonExistingBeerId_whenGetBeerById_thenReturn404NotFound() throws Exception {

        // given
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        // when
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());

        // then
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