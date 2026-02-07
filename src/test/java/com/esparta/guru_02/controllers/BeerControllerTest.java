package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.JpaAuditingConfig;
import com.esparta.guru_02.exceptions.NotFoundException;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.services.BeerService;
import tools.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(
        controllers = BeerController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JpaAuditingConfig.class
        )
)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;


    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    static BeerDTO beerDTO;
    static List<BeerDTO> beerDTOList;

    @BeforeAll
    static void setUp() {
        // GIVEN a valid BeerDTO used across tests
        beerDTO = BeerDTO.builder()
                .beerId(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();

        // GIVEN a list with a single beer
        beerDTOList = List.of(beerDTO);
    }

    @Test
    void givenValidBeerPayload_whenPostBeer_thenReturn201CreatedAndBeer() throws Exception {

        // GIVEN a valid beer payload
        UUID beerId = beerDTO.getBeerId();

        // AND the service returns the created beer
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerDTO);

        // WHEN the POST endpoint is called
        mockMvc.perform(post( BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))

                // THEN the response status is 201 CREATED
                .andExpect(status().isCreated())

                // AND the Location header points to the new resource
                .andExpect(header().string("Location",
                        "/api/v1/beer/" + beerId));
    }

    @Test
    void givenInvalidBeerPayload_whenPostBeer_thenReturn400BadRequest() throws Exception {

        // GIVEN an invalid beer payload (missing beerName)
        BeerDTO invalidBeerDTO = BeerDTO.builder()
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        // AND the service returns the created beer
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerDTO);

        // WHEN the POST endpoint is called
        mockMvc.perform(post( BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBeerDTO)))

                // THEN the response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest());
    }
    @Test
    void givenValidBeerId_whenGetBeerById_thenReturn200OkAndBeerJson() throws Exception {

        // GIVEN a valid beer ID
        UUID beerId = beerDTO.getBeerId();

        // AND the service returns the beer
        given(beerService.getBeerById(eq(beerId))).willReturn(beerDTO);

        // WHEN the GET-by-ID endpoint is called
        mockMvc.perform(get(BEER_PATH_ID, beerId))

                // THEN the response status is 200 OK
                .andExpect(status().isOk())

                // AND the response is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // AND the returned beer matches the expected values
                .andExpect(jsonPath("$.beerId", is(beerId.toString())))
                .andExpect(jsonPath("$.beerName", is("Galaxy Cat")));
    }

    @Test
    void givenBeersExist_whenGetBeers_thenReturn200OkAndSingleBeer() throws Exception {

        // GIVEN a list of beers returned by the service
        given(beerService.getAllBeers()).willReturn(beerDTOList);

        // WHEN the GET collection endpoint is called
        mockMvc.perform(get( BEER_PATH))

                // THEN the response status is 200 OK
                .andExpect(status().isOk())

                // AND the response is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // AND exactly one beer is returned
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void givenNonExistingBeerId_whenGetBeerById_thenReturn404NotFound() throws Exception {

        // GIVEN the service throws NotFoundException
        given(beerService.getBeerById(any(UUID.class)))
                .willThrow(NotFoundException.class);

        // WHEN the GET-by-ID endpoint is called with a random ID
        mockMvc.perform(get(BEER_PATH_ID, UUID.randomUUID()))

                // THEN the response status is 404 NOT FOUND
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidUpdatedBeerPayload_whenUpdateBeer_thenReturn200OkAndBeer() throws Exception {

        // GIVEN a valid beer ID and updated payload
        UUID beerId = beerDTO.getBeerId();

        // AND the service successfully updates the beer
        given(beerService.updateBeer(eq(beerId), any(BeerDTO.class)))
                .willReturn(beerDTO);

        // WHEN the PUT endpoint is called
        mockMvc.perform(put(BEER_PATH_ID, beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))

                // THEN the response status is 200 OK
                .andExpect(status().isOk())

                // AND the Location header is returned
                .andExpect(header().string("Location",
                        "/api/v1/beer/" + beerId));

        // AND the service was invoked correctly
        verify(beerService).updateBeer(eq(beerId), any(BeerDTO.class));
    }

    @Test
    void givenValidPatchBeerPayload_whenPatchBeer_thenReturn200Ok() throws Exception {

        // GIVEN a valid beer ID and partial update payload
        UUID beerId = beerDTO.getBeerId();

        // AND the service applies the patch successfully
        given(beerService.patchBeer(eq(beerId), any(BeerDTO.class)))
                .willReturn(beerDTO);

        // WHEN the PATCH endpoint is called
        mockMvc.perform(patch(BEER_PATH_ID, beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))

                // THEN the response status is 200 OK
                .andExpect(status().isOk())

                // AND the Location header is returned
                .andExpect(header().string("Location",
                        "/api/v1/beer/" + beerId));

        // AND the service received the correct arguments
        ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<BeerDTO> dtoCaptor = ArgumentCaptor.forClass(BeerDTO.class);

        verify(beerService).patchBeer(idCaptor.capture(), dtoCaptor.capture());

        assertEquals(beerId, idCaptor.getValue());
        assertEquals(beerDTO, dtoCaptor.getValue());
    }

    @Test
    void givenValidBeerId_whenDeleteBeer_thenReturn200Ok() throws Exception {

        // GIVEN a valid beer ID
        UUID beerId = beerDTO.getBeerId();

        // AND the service is prepared to delete the beer
        given(beerService.deleteById(any(UUID.class))).willReturn(beerDTO);

        // WHEN the DELETE endpoint is called
        mockMvc.perform(delete(BEER_PATH_ID, beerId))

                // THEN the response status is 200 OK
                .andExpect(status().isOk());

        // AND the service was called with the correct ID
        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteById(captor.capture());

        assertEquals(beerId, captor.getValue());
    }
}
