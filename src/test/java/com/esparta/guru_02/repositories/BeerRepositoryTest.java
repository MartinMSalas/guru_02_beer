package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.model.BeerStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
/*
 * Author: M
 * Date: 29-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    static Beer beer;
    static List<Beer> beerList;


    @BeforeAll
    static void setUp() {
        beer = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        beerList = List.of(beer);
    }
    @Test
    void givenValidBeer_whenSavingBeer_thenReturnBeerSaved(){
        // GIVEN

        // WHEN
        Beer savedBeer = beerRepository.save(beer);

        // THEN
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerId()).isNotNull();


    }
}