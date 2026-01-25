package com.esparta.guru_02.services;

import com.esparta.guru_02.model.Beer;

import java.util.List;
import java.util.UUID;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface BeerService {

    Beer getBeerById(UUID id);

    List<Beer>  listBeers();

    Beer saveNewBeer(Beer beer);

    Beer updateBeer(UUID beerId, Beer beer);

    Beer deleteById(UUID beerId);

    Beer patchBeer(UUID beerId, Beer beer);
}
