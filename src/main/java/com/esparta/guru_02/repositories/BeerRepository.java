package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface BeerRepository
        extends JpaRepository<Beer, UUID>,
        JpaSpecificationExecutor<Beer> {

    Optional<Beer> findFirstByOrderByCreatedDateAsc();

    //List<Beer> findByBeerNameContainingIgnoreCase(String beerName);


}
