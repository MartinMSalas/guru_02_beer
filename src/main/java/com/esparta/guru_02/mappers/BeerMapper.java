package com.esparta.guru_02.mappers;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.model.BeerDTO;
import org.mapstruct.*;

import java.util.List;

/*
 * Author: M
 * Date: 28-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 *
 * BeerMapper
 *
 * Responsibility:
 * - Convert between Beer (JPA Entity) and BeerDTO (API / transport model)
 *
 * Design rules enforced here:
 * - DTOs NEVER control JPA-managed fields
 * - Entities remain managed by Hibernate
 * - Auditing and optimistic locking stay intact
 */
@Mapper(componentModel = "spring")
public interface BeerMapper {

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
    BeerDTO beerToBeerDTO(Beer beer);

    /**
     * Entity List -> DTO List
     *
     * Convenience method for collection mapping.
     */
    List<BeerDTO> beerListToBeerDTOList(List<Beer> beers);

    /* =========================================================
       CREATE OPERATION (POST)
       ========================================================= */

    /**
     * DTO -> Entity (NEW entity)
     *
     * Used ONLY for CREATE operations.
     * A new Beer instance is created.
     *
     * JPA will:
     * - generate the ID
     * - set createdDate
     * - set lastModifiedDate
     * - initialize version
     */
    Beer beerDTOToBeer(BeerDTO dto);

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
    @Mapping(target = "beerId", ignore = true)                 // JPA identity
    @Mapping(target = "createdDate", ignore = true)        // auditing (creation)
    @Mapping(target = "lastModifiedDate", ignore = true)   // auditing (update)
    @Mapping(target = "version", ignore = true)            // optimistic locking
    void updateBeerFromDTO(BeerDTO dto, @MappingTarget Beer beer);

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
    @Mapping(target = "beerId", ignore = true)                 // JPA identity
    @Mapping(target = "createdDate", ignore = true)        // auditing (creation)
    @Mapping(target = "lastModifiedDate", ignore = true)   // auditing (update)
    @Mapping(target = "version", ignore = true)            // optimistic locking
    void patchBeerFromDTO(BeerDTO dto, @MappingTarget Beer beer);
}