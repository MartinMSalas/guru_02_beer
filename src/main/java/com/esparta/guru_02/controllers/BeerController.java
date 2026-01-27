package com.esparta.guru_02.controllers;

import com.esparta.guru_02.model.Beer;
import com.esparta.guru_02.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
//    public Beer getBeerById(UUID id){
//        log.debug("In BeerController.getBeerById() with id: {}", id);
//        return beerService.getBeerById(id);
//    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<Beer> createNewBeer(@RequestBody Beer beer){
        log.debug("In BeerController.createNewBeer() with beer: {}", beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beer.getId().toString());
        return new ResponseEntity<>(beerService.saveNewBeer(beer), headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> updateBeer(@PathVariable UUID beerId, @RequestBody Beer beer){
        log.debug("In BeerController.updateBeer() with id: {}", beerId);
        Beer beerUpdated = beerService.updateBeer(beerId, beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beerUpdated.getId().toString());
        return new ResponseEntity<>(beerUpdated, headers, HttpStatus.OK);

    }
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){
        log.debug("In BeerController.listBeers()");
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public Beer getBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.getBeer() with id: {}", beerId);

        return beerService.getBeerById(beerId);
    }
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> deleteBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.deleteBeer() with id: {}", beerId);
        Beer deletedBeer = beerService.deleteById(beerId);

        return new ResponseEntity<>(deletedBeer, HttpStatus.OK);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> patchBeer(@PathVariable UUID beerId, @RequestBody Beer beer) {
        log.debug("In BeerController.patchBeer() with id: {}", beerId);
        Beer patchedBeer = beerService.patchBeer(beerId, beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + patchedBeer.getId().toString());
        return new ResponseEntity<>(patchedBeer, headers, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Not Found");
        pd.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }
}
