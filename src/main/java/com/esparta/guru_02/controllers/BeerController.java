package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.Beer;
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
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;


    public Beer getBeerById(UUID id){
        log.debug("In BeerController.getBeerById() with id: {}", id);
        return beerService.getBeerById(id);
    }

    @PostMapping("")
    public ResponseEntity<Beer> createNewBeer(@RequestBody Beer beer){
        log.debug("In BeerController.createNewBeer() with beer: {}", beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beer.getId().toString());
        return new ResponseEntity<>(beerService.saveNewBeer(beer), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateBeer(@PathVariable UUID beerId, @RequestBody Beer beer){
        log.debug("In BeerController.updateBeer() with id: {}", beerId);
        Beer beerUpdated = beerService.updateBeer(beerId, beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beerUpdated.getId().toString());
        return new ResponseEntity<>(beerUpdated, headers, HttpStatus.OK);

    }
    @GetMapping("")
    public List<Beer> listBeers(){
        log.debug("In BeerController.listBeers()");
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Beer getBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.getBeer() with id: {}", beerId);
        return beerService.getBeerById(beerId);
    }
    @DeleteMapping("/{beerId}")
    public ResponseEntity<Beer> deleteBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.deleteBeer() with id: {}", beerId);
        Beer deletedBeer = beerService.deleteById(beerId);

        return new ResponseEntity<>(deletedBeer, HttpStatus.OK);
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity<Beer> patchBeer(@PathVariable UUID beerId, @RequestBody Beer beer) {
        log.debug("In BeerController.patchBeer() with id: {}", beerId);
        Beer patchedBeer = beerService.patchBeer(beerId, beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + patchedBeer.getId().toString());
        return new ResponseEntity<>(patchedBeer, headers, HttpStatus.OK);
    }
}
