package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.entities.BeerOrder;
import com.esparta.guru_02.entities.Customer;
import com.esparta.guru_02.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    private Customer customer;
    private Beer beer;
    private BeerOrder beerOrder;

    @BeforeEach
    void setUp() {
        // Create and save a Customer
        customer = Customer.builder()
                .customerName("Test Customer")
                .build();
        customer = customerRepository.save(customer);

        // Create and save a Beer
        beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle(BeerStyle.AMERICAN_BARLEYWINE)
                .upc("123456789012")
                .price(BigDecimal.valueOf(11.22))
                .build();

        beer = beerRepository.save(beer);

        // Create and save a BeerOrder
        beerOrder = BeerOrder.builder()
                .customer(customer)
                .customerRef("Test Order Ref")
                .build();
        beerOrder = beerOrderRepository.save(beerOrder);
    }

    @Test
    void testBeerOrders(){
        System.out.println(beerOrderRepository.count());
    }



}