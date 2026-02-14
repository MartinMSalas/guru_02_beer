package com.esparta.guru_02.repositories;

import com.esparta.guru_02.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 * Author: M
 * Date: 13-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
