package com.esparta.guru_02.services;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.mappers.BeerMapper;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.repositories.BeerRepository;
import jakarta.persistence.EntityNotFoundException;
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

    private List<Beer> beerList;
    private List<BeerDTO> beerDTOList;

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    public BeerServiceImpl(BeerMapper beerMapper, BeerRepository beerRepository){
        this.beerMapper = beerMapper;
        this.beerRepository = beerRepository;
        this.beerList = new ArrayList<>();
        Beer beer1 = Beer.builder()
                .beerId(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789012")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(1223)
                .build();
        Beer beer2 = Beer.builder()
                .beerId(UUID.randomUUID())
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456789013")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(1223)
                .build();
        Beer beer3 = Beer.builder()
                .beerId(UUID.randomUUID())
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789014")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(1223)
                .build();
        beerList.add(beer1);
        beerList.add(beer2);
        beerList.add(beer3);

        beerDTOList = beerMapper.beerListToBeerDTOList(beerList);

    }

    @Override
    public List<BeerDTO> listBeers(){
        // return beerMap.values().stream().toList();
        log.debug("List Beers - in Service");
        List<Beer> beerListSaved = beerRepository.findAll();

        return beerMapper.beerListToBeerDTOList(beerListSaved);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        log.debug("Get BeerDTO by Id - in Service. Id: {}", beerId);

        if (beerId == null) {
            throw new IllegalArgumentException("Beer ID must be provided");
        }

        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Beer not found with id " + beerId
                ));

        log.debug("Loaded Beer: {}", beer);

        return beerMapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        log.debug("Save New BeerDTO - in Service. BeerDTO: {}", beerDTO);

        Beer beerToSave = beerMapper.beerDTOToBeer(beerDTO);
        Beer savedBeer = beerRepository.save(beerToSave);
        log.debug("Beer entity saved: {}", savedBeer);

        return beerMapper.beerToBeerDTO(savedBeer);
    }


    @Override
    public BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO) {
        log.debug("Update BeerDTO - in Service. Id: {}, BeerDTO: {}", beerId, beerDTO);
        // Validate input (PUT requires ID)
        if (beerId == null) {
            throw new IllegalArgumentException("Beer ID must be provided for update");
        }

        // Load the managed entity
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Beer not found with id " + beerDTO.getBeerId()
                ));
        log.debug("Loaded Beer entity for update: {}", beer);
        //  Apply DTO values to the managed entity
        //     - does NOT touch id
        //     - does NOT touch auditing fields
        //     - does NOT touch version
        beerMapper.updateBeerFromDTO(beerDTO, beer);

        //  Explicit save (optional but clear)
        Beer savedBeer = beerRepository.save(beer);
        log.debug("Beer entity updated and saved: {}", savedBeer);
        //  Return fresh DTO (with updated auditing + version)
        return beerMapper.beerToBeerDTO(savedBeer);
    }




    @Override
    public BeerDTO deleteById(UUID beerId) {
        log.debug("Delete BeerDTO - in Service. Id: {}", beerId);
        // Validate input (DELETE requires ID)
        if (beerId == null) {
            throw new IllegalArgumentException("Beer ID must be provided for delete");
        }

        //  Load managed entity (fail fast if not found)
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Beer not found with id " + beerId
                ));

        log.debug("Loaded Beer entity for deletion: {}", beer);
        //  Delete managed entity
        beerRepository.delete(beer);

        return beerMapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO patchBeer(UUID beerId, BeerDTO beerDTO) {
        log.debug("Patch BeerDTO - in Service. Id: {}, BeerDTO: {}", beerId, beerDTO);
        // Validate input (patch requires ID)
        if (beerId == null) {
            throw new IllegalArgumentException("Beer ID must be provided for patch");
        }
        //  Load managed entity (fail fast if not found)
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Beer not found with id " + beerId
                ));

        log.debug("Loaded Beer entity for patch: {}", beer);
        //  Apply non-null DTO values to the managed entity
        //     - does NOT touch id
        //     - does NOT touch auditing fields
        //     - does NOT touch version
        beerMapper.patchBeerFromDTO(beerDTO, beer);
        //  Explicit save (optional but clear)
        Beer savedBeer = beerRepository.save(beer);
        log.debug("Beer entity patched and saved: {}", savedBeer);
        //  Return fresh DTO (with updated auditing + version)
        return beerMapper.beerToBeerDTO(savedBeer);

    }
}
