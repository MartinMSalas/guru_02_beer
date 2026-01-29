package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
