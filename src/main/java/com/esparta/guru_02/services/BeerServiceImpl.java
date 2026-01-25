package com.esparta.guru_02.services;

import com.esparta.guru_02.model.Beer;
import com.esparta.guru_02.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789013")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(1223)
                .build();
        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789014")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(1223)
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);


    }

    @Override
    public List<Beer> listBeers(){
        // return beerMap.values().stream().toList();
        log.debug("List Beers - in Service");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        log.debug("Save New Beer - in Service. Beer: {}", beer);
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        log.debug("New Beer saved: {}", savedBeer);
        return savedBeer;
    }

    @Override
    public Beer updateBeer(UUID id, Beer beer) {
        log.debug("Update Beer - in Service. Id: {}, Beer: {}", id, beer);
        Beer existingBeer = beerMap.get(id);
        if (existingBeer != null) {
            existingBeer.setBeerName(beer.getBeerName());
            existingBeer.setBeerStyle(beer.getBeerStyle());
            existingBeer.setUpc(beer.getUpc());
            existingBeer.setPrice(beer.getPrice());
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            log.debug("Beer updated: {}", existingBeer);
            return existingBeer;
        } else {
            log.warn("Beer with id {} not found for update.", id);
            return null;
        }
    }

    @Override
    public Beer deleteById(UUID beerId) {
        log.debug("Delete Beer - in Service. Id: {}", beerId);
        if (beerMap.containsKey(beerId)) {
            Beer removedBeer = beerMap.remove(beerId);
            log.debug("Beer deleted: {}", removedBeer);
            return removedBeer;
        }
        log.debug("Beer with id {} not found for deletion.", beerId);
        return null;
    }

    @Override
    public Beer patchBeer(UUID beerId, Beer beer) {
        log.debug("Patch Beer - in Service. Id: {}, Beer: {}", beerId, beer);
        Beer existingBeer = beerMap.get(beerId);
        if (existingBeer != null) {
            if (beer.getBeerName() != null) {
                existingBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                existingBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getUpc() != null) {
                existingBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null) {
                existingBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null) {
                existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            log.debug("Beer patched: {}", existingBeer);
            return existingBeer;
        }
        log.debug("Beer with id {} not found for patching.", beerId);
        return null;
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer By Id - in Service. Id: {}", id);
        return Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1123)
                .build();
    }
}
