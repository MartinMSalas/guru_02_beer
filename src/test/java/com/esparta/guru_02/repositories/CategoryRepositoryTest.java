package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EnableJpaAuditing
class CategoryRepositoryTest {


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer beer;

    @BeforeEach
    void setUp() {
        beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle(com.esparta.guru_02.model.BeerStyle.AMERICAN_BARLEYWINE)
                .upc("123456789012")
                .price(java.math.BigDecimal.valueOf(11.22))
                .quantityOnHand(111)
                .build();
        beerRepository.save(beer);

    }

    @Test
    void testAddCategory(){
        Category savedCategory = categoryRepository.save(Category.builder()
                .categoryDescription("Test Category")
                .build());

        beer.addCategory(savedCategory);
        Beer savedBeer = beerRepository.save(beer);

        System.out.println(savedBeer.getBeerName());
        assertTrue(savedBeer.getCategories().contains(savedCategory));
    }
}