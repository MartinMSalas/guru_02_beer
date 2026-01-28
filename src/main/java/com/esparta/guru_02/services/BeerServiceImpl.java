package com.esparta.guru_02.services;

import com.esparta.guru_02.model.BeerDTO;
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

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();
        BeerDTO beerDTO1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        BeerDTO beerDTO2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789013")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(1223)
                .build();
        BeerDTO beerDTO3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789014")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(1223)
                .build();

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);


    }

    @Override
    public List<BeerDTO> listBeers(){
        // return beerMap.values().stream().toList();
        log.debug("List Beers - in Service");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        log.debug("Save New BeerDTO - in Service. BeerDTO: {}", beerDTO);
        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .build();
        beerMap.put(savedBeerDTO.getId(), savedBeerDTO);
        log.debug("New BeerDTO saved: {}", savedBeerDTO);
        return savedBeerDTO;
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        log.debug("Update BeerDTO - in Service. Id: {}, BeerDTO: {}", id, beerDTO);
        BeerDTO existingBeerDTO = beerMap.get(id);
        if (existingBeerDTO != null) {
            existingBeerDTO.setBeerName(beerDTO.getBeerName());
            existingBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
            existingBeerDTO.setUpc(beerDTO.getUpc());
            existingBeerDTO.setPrice(beerDTO.getPrice());
            existingBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
            log.debug("BeerDTO updated: {}", existingBeerDTO);
            return existingBeerDTO;
        } else {
            log.warn("BeerDTO with id {} not found for update.", id);
            return null;
        }
    }

    @Override
    public BeerDTO deleteById(UUID beerId) {
        log.debug("Delete BeerDTO - in Service. Id: {}", beerId);
        if (beerMap.containsKey(beerId)) {
            BeerDTO removedBeerDTO = beerMap.remove(beerId);
            log.debug("BeerDTO deleted: {}", removedBeerDTO);
            return removedBeerDTO;
        }
        log.debug("BeerDTO with id {} not found for deletion.", beerId);
        return null;
    }

    @Override
    public BeerDTO patchBeer(UUID beerId, BeerDTO beerDTO) {
        log.debug("Patch BeerDTO - in Service. Id: {}, BeerDTO: {}", beerId, beerDTO);
        BeerDTO existingBeerDTO = beerMap.get(beerId);
        if (existingBeerDTO != null) {
            if (beerDTO.getBeerName() != null) {
                existingBeerDTO.setBeerName(beerDTO.getBeerName());
            }
            if (beerDTO.getBeerStyle() != null) {
                existingBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
            }
            if (beerDTO.getUpc() != null) {
                existingBeerDTO.setUpc(beerDTO.getUpc());
            }
            if (beerDTO.getPrice() != null) {
                existingBeerDTO.setPrice(beerDTO.getPrice());
            }
            if (beerDTO.getQuantityOnHand() != null) {
                existingBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            log.debug("BeerDTO patched: {}", existingBeerDTO);
            return existingBeerDTO;
        }
        log.debug("BeerDTO with id {} not found for patching.", beerId);
        return null;
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get BeerDTO By Id - in Service. Id: {}", id);
        BeerDTO fakeBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1123)
                .build();
        return Optional.of(fakeBeerDTO);
    }
}
