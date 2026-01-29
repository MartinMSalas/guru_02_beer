package com.esparta.guru_02.mappers;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.entities.Customer;
import com.esparta.guru_02.model.BeerDTO;
import com.esparta.guru_02.model.CustomerDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 *
 * CustomerMapper
 *
 * Responsibility:
 * - Convert between Customer (JPA Entity) and CustomerDTO (API / transport model)
 *
 * Design rules enforced here:
 * - DTOs NEVER control JPA-managed fields
 * - Entities remain managed by Hibernate
 * - Auditing and optimistic locking stay intact
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

     /* =========================================================
       READ OPERATIONS
       ========================================================= */

    /**
     * Entity -> DTO
     *
     * Used for:
     * - GET responses
     * - Returning results after CREATE / UPDATE
     *
     * All fields are safe to expose.
     */
    CustomerDTO customerToCustomerDTO(Customer customer);

    /**
     * Entity List -> DTO List
     *
     * Convenience method for collection mapping.
     */
    List<CustomerDTO> customerListToCustomerDTOList(List<Customer> customers);

     /* =========================================================
       CREATE OPERATION (POST)
       ========================================================= */

    /**
     * DTO -> Entity (NEW entity)
     *
     * Used ONLY for CREATE operations.
     * A new Customer instance is created.
     *
     * JPA will:
     * - generate the ID
     * - set createdDate
     * - set lastModifiedDate
     * - initialize version
     */
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    /* =========================================================
       UPDATE OPERATION (PUT)
       ========================================================= */

    /**
     * DTO -> EXISTING Entity (FULL UPDATE)
     *
     * Used for PUT semantics:
     * - Client sends the full representation
     * - Null values overwrite existing values
     *
     * IMPORTANT:
     * - The entity instance is already managed by JPA
     * - We MUST NOT replace it
     * - We MUST NOT touch JPA-owned fields
     */
    @Mapping(target = "customerId", ignore = true)                 // JPA identity
    @Mapping(target = "createdDate", ignore = true)        // auditing (creation)
    @Mapping(target = "lastModifiedDate", ignore = true)   // auditing (update)
    @Mapping(target = "version", ignore = true)            // optimistic locking
    void updateCustomerFromDTO(CustomerDTO customerDTO, @MappingTarget Customer customer);


    /* =========================================================
       PARTIAL UPDATE OPERATION (PATCH)
       ========================================================= */

    /**
     * DTO -> EXISTING Entity (PARTIAL UPDATE)
     *
     * Used for PATCH semantics:
     * - Client sends only fields to change
     * - Null values MUST NOT overwrite existing values
     *
     * In addition to ignoring JPA-owned fields,
     * we also ignore null properties.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customerId", ignore = true)                 // JPA identity
    @Mapping(target = "createdDate", ignore = true)        // auditing (creation)
    @Mapping(target = "lastModifiedDate", ignore = true)   // auditing (update)
    @Mapping(target = "version", ignore = true)            // optimistic locking
    void patchCustomerFromDTO(CustomerDTO customerDTO, @MappingTarget Customer customer);
}
