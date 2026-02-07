package com.esparta.guru_02.bootstrap;

import com.esparta.guru_02.entities.Customer;
import com.esparta.guru_02.entities.Beer;

import com.esparta.guru_02.mappers.BeerMapper;
import com.esparta.guru_02.model.BeerCSVRecord;
import com.esparta.guru_02.model.BeerStyle;
import com.esparta.guru_02.repositories.CustomerRepository;
import com.esparta.guru_02.repositories.BeerRepository;

import com.esparta.guru_02.services.BeerCSVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Author: M
 * Date: 29-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;
    private final BeerCSVService beerCSVService;
    private final BeerMapper beerMapper;

    @Override
    public void run(String... args) {
        log.debug("In bootstrap; ");
        // ======== Beer ======== */
        long beerCount = beerRepository.count();
        if(beerCount == 0) {
            log.debug("Loading Beer Data");
            //loadBeerData();
            loadCSVBeerData();
            beerCount = beerRepository.count();
        }

        /* ======== Beer CSV  ======== */

        /* ========= Customer ======== */
        long customerCount = customerRepository.count();
        if(customerCount == 0) {
            log.debug("Loading Customer Data");
            loadCustomerData();
            customerCount = customerRepository.count();
        }

        /* ========= Log ========= */
        log.debug("Beer Count: {}", beerCount);
        log.debug("Saved Beer: {}", beerRepository.findFirstByOrderByCreatedDateAsc().orElseThrow(() -> new NoSuchElementException("No beers found after bootstrap")));
        log.debug("Customer Count: {}", customerCount);
        log.debug("Saved Customer: {}", customerRepository.findFirstByOrderByCreatedDateAsc().orElseThrow(() -> new NoSuchElementException("No customers found after bootstrap")));

    }
    private void loadCSVBeerData(){
        log.debug("Loading Beer CSV Data");
        String beerFileName = "beers.csv";
        String pathToFile = "src/main/java/com/esparta/guru_02/model/csv/" + beerFileName;
        List<BeerCSVRecord> beerCSVRecords = beerCSVService.beerCSVRecords(pathToFile);

        List<Beer> beers = beerMapper.csvToBeerList(beerCSVRecords);
        List<Beer> savedBeers = beerRepository.saveAll(beers);
        log.debug("Saved {} beers from CSV", savedBeers.size());

    }

    private void loadBeerData() {
                beerRepository.saveAll(List.of(
                Beer.builder().beerName("Galaxy Cat").beerStyle(BeerStyle.CALIFORNIA_COMMON_STEAM_BEER).upc("123456789012")
                        .price(new BigDecimal("12.99")).quantityOnHand(1223).build(),
                Beer.builder().beerName("Crank").beerStyle(BeerStyle.SCOTCH_ALE).upc("123456789013")
                        .price(new BigDecimal("11.99")).quantityOnHand(1223).build(),
                Beer.builder().beerName("Sunshine City").beerStyle(BeerStyle.AMERICAN_BARLEYWINE).upc("123456789014")
                        .price(new BigDecimal("13.99")).quantityOnHand(1223).build()
        ));
    }

    private void loadCustomerData() {
        customerRepository.saveAll(List.of(
                Customer.builder().customerName("Don Pepe").build(),
                Customer.builder().customerName("Maria Luisa").build(),
                Customer.builder().customerName("Juan Carlos").build()
        ));
    }


}
