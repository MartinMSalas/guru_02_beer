package com.esparta.guru_02.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {

    /* =========================
       IDENTITY
       ========================= */

    private UUID beerId;
    /**
     * External identifier (CSV, supplier, legacy system, etc.)
     * Optional – may be null.
     */
    private Integer externalBeerId;


    /* =========================
       DOMAIN DATA
       ========================= */

    @NotBlank
    @Size(max = 255)
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotBlank
    @Size(max = 50)
    private String upc;

    @NotNull
    @Min(0)
    private Integer quantityOnHand;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    /* =========================
       AUDITING (READ-ONLY)
       ========================= */

    private Instant createdDate;
    private Instant lastModifiedDate;

    /* =========================
       OPTIMISTIC LOCKING
       ========================= */

    private Long version;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BeerDTO beerDTO = (BeerDTO) o;
        return Objects.equals(beerId, beerDTO.beerId) && Objects.equals(externalBeerId, beerDTO.externalBeerId) && Objects.equals(beerName, beerDTO.beerName) && beerStyle == beerDTO.beerStyle && Objects.equals(upc, beerDTO.upc) && Objects.equals(quantityOnHand, beerDTO.quantityOnHand) && Objects.equals(price, beerDTO.price) && Objects.equals(version, beerDTO.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beerId, externalBeerId, beerName, beerStyle, upc, quantityOnHand, price, version);
    }
}