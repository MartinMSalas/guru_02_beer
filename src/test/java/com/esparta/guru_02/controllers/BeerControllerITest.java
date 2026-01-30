package com.esparta.guru_02.controllers;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerControllerITest {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;


    @Test
    void givenValidBeerId_whenGetBeerById_thenReturn200OKAndSingleBeer(){
        // GIVEN
        UUID beerId = beerRepository.findAll().getFirst().getBeerId();

        // WHEN
        ResponseEntity<BeerDTO> response = beerController.getBeerById(beerId);
        // THEN
        assertThat().isNotNull();
    }

    @Test
    void givenBeersExist_whenGetBeers_thenReturn200OkAndSingleBeer() throws Exception {
        List<BeerDTO> beerDTOList = beerController.listBeers();

        assertThat(beerDTOList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void givenNoBeersExist_whenGetBeers_thenReturnEmptyList() {

        beerRepository.deleteAll();
        List<BeerDTO> beerDTOList = beerController.listBeers();

        assertThat(beerDTOList.size()).isEqualTo(0);
    }

}