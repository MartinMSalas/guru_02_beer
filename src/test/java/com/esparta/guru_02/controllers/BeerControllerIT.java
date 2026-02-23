package com.esparta.guru_02.controllers;

import com.esparta.guru_02.configuration.SpringSecurityConfig;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.model.BeerStyle;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
 * Author: M
 * Date: 07-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Transactional
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("localmysql")
@Import(SpringSecurityConfig.class)
public class BeerControllerIT {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2");

    static final String BEER_PATH = "/api/v1/beer";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /* =========================================================
       TEST DATA
       ========================================================= */

    private BeerDTO buildValidBeerDTO() {
        return BeerDTO.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.AMERICAN_IPA)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();
    }

/*
    private BeerDTO getFirstBeerFromList() throws Exception {
        MvcResult result = mockMvc.perform(get(BEER_PATH)
                .with(httpBasic(USERNAME,PASSWORD)))
                .andExpect(status().isOk())
                .andReturn();


        List<BeerDTO> beers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), BeerDTO[].class));

        assertThat(beers).hasSizeGreaterThan(0);

        BeerDTO firstBeer = beers.getFirst();
        assertThat(firstBeer.getBeerId()).isNotNull();
        return firstBeer;
    }
*/
    private RequestPostProcessor validJwt() {
        return jwt().jwt(jwt -> jwt
                .subject("messaging-client")
                .notBefore(Instant.now().minusSeconds(5))
                .claim("scope", "message-read message-write")
        );
    }

    private BeerDTO createBeerViaApi() throws Exception {
        MvcResult result = mockMvc.perform(post(BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildValidBeerDTO()))
                        .with(validJwt()))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BeerDTO.class
        );
    }

    /*
    // To get an existing beer
    private BeerDTO getFirstBeerFromList() throws Exception {

        MvcResult result = mockMvc.perform(get(BEER_PATH)
                        .with(jwt().jwt(jwt -> {
                            jwt.claims(claims -> {
                                claims.put("scope", "message-read");
                                claims.put("scope", "message-write");
                            })
                                    .subject("messaging-client")
                                    .notBefore(Instant.now().minusSeconds(5L));
                        })))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        JsonNode content = root.get("content");

        assertThat(content).isNotNull();
        assertThat(content.isArray()).isTrue();
        assertThat(content.size()).isGreaterThan(0);

        BeerDTO firstBeer = objectMapper.treeToValue(content.get(0), BeerDTO.class);

        assertThat(firstBeer.getBeerId()).isNotNull();

        return firstBeer;
    }
    */

    /* =========================================================
       POST
       ========================================================= */

    @Test
    void givenValidBeerPayload_whenPostBeer_then201Created() throws Exception {

        mockMvc.perform(post(BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildValidBeerDTO()))
                        .with(validJwt()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.beerId").isNotEmpty())
                .andExpect(jsonPath("$.beerStyle").value("AMERICAN_IPA"));
    }

    @Test
    void givenInvalidBeerPayload_whenPostBeer_then400BadRequest() throws Exception {

        BeerDTO invalid = BeerDTO.builder().build();

        mockMvc.perform(post(BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid))
                        .with(validJwt()))
                .andExpect(status().isBadRequest());
    }



    /* =========================================================
       GET BY ID
       ========================================================= */

    @Test
    void givenExistingBeerId_whenGetBeerById_then200Ok() throws Exception {

        BeerDTO created = createBeerViaApi();

        mockMvc.perform(get(BEER_PATH + "/" + created.getBeerId())
                        .with(validJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerId").value(created.getBeerId().toString()))
                .andExpect(jsonPath("$.beerName").value(created.getBeerName()));
    }

    @Test
    void givenNonExistingBeerId_whenGetBeerById_then404NotFound() throws Exception {

        mockMvc.perform(get(BEER_PATH + "/" + UUID.randomUUID())
                        .with(validJwt()))
                .andExpect(status().isNotFound());
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    @Test
    void givenBeersExist_whenGetAllBeers_then200AndXTotalCount() throws Exception {
        createBeerViaApi();
        mockMvc.perform(get(BEER_PATH)
                        .with(validJwt()))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()", greaterThanOrEqualTo(1)));
    }
    void givenInvalidPaginationParams_whenGetAllBeers_then400() throws Exception {

        mockMvc.perform(get(BEER_PATH)
                        .param("page", "-1")
                        .param("size", "0")
                        .with(validJwt()))
                .andExpect(status().isBadRequest());
    }

    /* =========================================================
       PUT
       ========================================================= */

    @Test
    void givenValidUpdatePayload_whenPutBeer_then200Ok() throws Exception {

        BeerDTO created = createBeerViaApi();
        created.setBeerName("Updated Beer");

        mockMvc.perform(put(BEER_PATH + "/" + created.getBeerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created))
                        .with(validJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerName").value("Updated Beer"));
    }

    /* =========================================================
       PATCH
       ========================================================= */

    @Test
    void givenPartialPayload_whenPatchBeer_thenOnlyProvidedFieldsUpdated() throws Exception {

        BeerDTO created = createBeerViaApi();

        BeerDTO patch = BeerDTO.builder()
                .beerName("Patched Beer")
                .build();

        mockMvc.perform(patch(BEER_PATH + "/" + created.getBeerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch))
                        .with(validJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerName").value("Patched Beer"))
                .andExpect(jsonPath("$.beerStyle").value(created.getBeerStyle().name()));
    }

    /* =========================================================
       DELETE
       ========================================================= */

    @Test
    void givenExistingBeerId_whenDeleteBeer_then200Ok() throws Exception {

        BeerDTO created = createBeerViaApi();

        mockMvc.perform(delete(BEER_PATH + "/" + created.getBeerId())
                        .with(validJwt()))
                .andExpect(status().isOk());

        mockMvc.perform(get(BEER_PATH + "/" + created.getBeerId())
                        .with(validJwt()))
                .andExpect(status().isNotFound());
    }
}

