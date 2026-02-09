package com.esparta.guru_02.controllers;

import com.esparta.guru_02.exceptions.BadRequestException;
import com.esparta.guru_02.model.BeerDTO;

import com.esparta.guru_02.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(BeerController.BEER_PATH)
@Slf4j
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID =  "/{beerId}";

    private static final String HEADER_TOTAL_COUNT = "X-Total-Count";

    private final BeerService beerService;


    /**
        * Creates a new beer.
        *
        * Parameters:
        * - beerDTO: Beer data to create (must include all required fields)
        *
        * Contract:
        * - 201 Created with created BeerDTO if creation is successful
        * - 400 Bad Request if input data is invalid
    */
    @PostMapping()
    public ResponseEntity<BeerDTO> createNewBeer(@Validated @RequestBody BeerDTO beerDTO){
        log.debug("In BeerController.createNewBeer() with beerDTO: {}", beerDTO);
        BeerDTO beerDTOSaved =  beerService.saveNewBeer(beerDTO);
        log.debug("Saved BeerDTO: {}", beerDTOSaved);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beerDTOSaved.getBeerId().toString());
        return new ResponseEntity<>(beerService.saveNewBeer(beerDTO), headers, HttpStatus.CREATED);
    }

    /**
        * Returns a beer by ID.
        *
        * Parameters:
        * - beerId: ID of the beer to retrieve
        *
        * Contract:
        * - 200 OK with BeerDTO if found
        * - 404 Not Found if no beer with the given ID exists
    */
    @GetMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId){
        log.debug("In BeerController.getBeer() with id: {}", beerId);
        BeerDTO beerDTO = beerService.getBeerById(beerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + beerDTO.getBeerId().toString());
        return new ResponseEntity<>(beerDTO,headers, HttpStatus.OK) ;
    }


    /**
        * Returns all beers.
        *
        *  Parameters:
        *  - beerName (optional): Filter by beer name (case-insensitive, partial match)
        *  - beerStyle (optional): Filter by beer style (case-insensitive, exact match)
        *  - page (optional, default=0): Page number for pagination (0-based)
        *  - size (optional, default=25): Number of items per page (max 100)
        *
        *
        * Contract:
        * - 200 OK with list (possibly empty)
        * - Never returns null
    */
    @GetMapping()
    public ResponseEntity<List<BeerDTO>> getAllBeers(
            @RequestParam(required = false) String beerName,
            @RequestParam(required = false) String beerStyle,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer size
            // @RequestParam(required = false) Integer size
    ) {

        log.debug("In BeerController.getAllBeers() with filters - beerName: {}, beerStyle: {}, page: {}, size: {}", beerName, beerStyle, page, size);

        if ((page < 0) || ( size <= 0) || size > 100) {
            throw new BadRequestException("Invalid pagination parameters");
        }

        // For now, ignoring pagination parameters
        List<BeerDTO> beers = beerService.getAllBeers( beerName, beerStyle , 0, 25);
        // Add X-Total-Count header if pagination is implemented in the future
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().mustRevalidate());
        headers.add(HEADER_TOTAL_COUNT, String.valueOf(beers.size()));
        return new ResponseEntity<>(beers, headers, HttpStatus.OK);
    }

    /**
        * Updates an existing beer by ID.
        *
        * Parameters:
        * - beerId: ID of the beer to update
        * - beerDTO: Updated beer data (must include all required fields)
        *
        * Contract:
        * - 200 OK with updated BeerDTO if update is successful
        * - 400 Bad Request if input data is invalid
        * - 404 Not Found if no beer with the given ID exists
    */
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO){
        log.debug("In BeerController.updateBeer() with id: {}", beerId);
        BeerDTO beerDTOUpdated = beerService.updateBeer(beerId, beerDTO);
        log.debug("Updated BeerDTO: {}", beerDTOUpdated);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + beerDTOUpdated.getBeerId().toString());
        return new ResponseEntity<>(beerDTOUpdated, headers, HttpStatus.OK);
    }

    /**
     * Partially updates a beer by ID.
     *
     * Parameters:
     * - beerId: ID of the beer to update
     * - beerDTO: Beer data to update (only non-null fields will be updated)
     *
     * Contract:
     * - 200 OK with updated BeerDTO if update is successful
     * - 400 Bad Request if input data is invalid
     * - 404 Not Found if no beer with the given ID exists
     */
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> patchBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO) {
        log.debug("In BeerController.patchBeer() with id: {}", beerId);
        BeerDTO patchedBeerDTO = beerService.patchBeer(beerId, beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + patchedBeerDTO.getBeerId().toString());
        return new ResponseEntity<>(patchedBeerDTO, headers, HttpStatus.OK);
    }

    /**
        * Deletes a beer by ID.
        *
        * Parameters:
        * - beerId: ID of the beer to delete
        *
        * Contract:
        * - 200 OK with deleted BeerDTO if deletion is successful
        * - 404 Not Found if no beer with the given ID exists
    */
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> deleteBeer(@PathVariable UUID beerId){
        log.debug("In BeerController.deleteBeer() with id: {}", beerId);
        BeerDTO deletedBeerDTO = beerService.deleteById(beerId);

        return new ResponseEntity<>(deletedBeerDTO, HttpStatus.OK);
    }


}

