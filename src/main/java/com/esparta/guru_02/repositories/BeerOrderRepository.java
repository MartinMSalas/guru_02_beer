package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 * Author: m
 * Date: 10/2/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

}
