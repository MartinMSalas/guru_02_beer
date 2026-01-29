package com.esparta.guru_02.services;


import com.esparta.guru_02.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface BeerService {

    BeerDTO getBeerById(UUID id);

    List<BeerDTO>  listBeers();

    BeerDTO saveNewBeer(BeerDTO BeerDTO);

    BeerDTO updateBeer(UUID beerId, BeerDTO BeerDTO);

    BeerDTO deleteById(UUID beerId);

    BeerDTO patchBeer(UUID beerId, BeerDTO BeerDTO);
}
