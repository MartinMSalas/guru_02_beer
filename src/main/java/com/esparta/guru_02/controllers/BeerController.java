package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@AllArgsConstructor
@RestController
@Slf4j

public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

//    @GetMapping(BEER_PATH_ID)
//    public BeerDTO getBeerById(UUID id){
//        log.debug("In BeerController.getBeerById() with id: {}", id);
//        return beerService.getBeerById(id);
//    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> createNewBeer(@RequestBody BeerDTO beerDTO){
        log.debug("In BeerController.createNewBeer() with beerDTO: {}", beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beerDTO/" + beerDTO.getBeerId().toString());
        return new ResponseEntity<>(beerService.saveNewBeer(beerDTO), headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO){
        log.debug("In BeerController.updateBeer() with id: {}", beerId);
        BeerDTO beerDTOUpdated = beerService.updateBeer(beerId, beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beerDTO/" + beerDTOUpdated.getBeerId().toString());
        return new ResponseEntity<>(beerDTOUpdated, headers, HttpStatus.OK);

    }
    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers(){
        log.debug("In BeerController.listBeers()");
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId){
        log.debug("In BeerController.getBeer() with id: {}", beerId);
        BeerDTO beerDTO = beerService.getBeerById(beerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beerDTO/" + beerDTO.getBeerId().toString());
        return new ResponseEntity<>(beerDTO,headers, HttpStatus.OK) ;
    }
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> deleteBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.deleteBeer() with id: {}", beerId);
        BeerDTO deletedBeerDTO = beerService.deleteById(beerId);

        return new ResponseEntity<>(deletedBeerDTO, HttpStatus.OK);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> patchBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO) {
        log.debug("In BeerController.patchBeer() with id: {}", beerId);
        BeerDTO patchedBeerDTO = beerService.patchBeer(beerId, beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beerDTO/" + patchedBeerDTO.getBeerId().toString());
        return new ResponseEntity<>(patchedBeerDTO, headers, HttpStatus.OK);
    }


}
