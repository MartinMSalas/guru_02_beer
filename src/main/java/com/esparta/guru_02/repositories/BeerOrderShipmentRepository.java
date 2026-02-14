package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.BeerOrderShipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 * Author: M
 * Date: 13-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface BeerOrderShipmentRepository extends JpaRepository<BeerOrderShipment, UUID> {
}
