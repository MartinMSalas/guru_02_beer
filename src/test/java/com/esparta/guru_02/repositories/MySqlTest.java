package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
 * Author: m
 * Date: 3/2/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
@Testcontainers
@SpringBootTest
public class MySqlTest {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2");

    @Autowired
    DataSource dataSource;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(0);
    }


}
