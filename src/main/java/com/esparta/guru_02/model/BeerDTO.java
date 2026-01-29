package com.esparta.guru_02.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerDTO {

    private UUID beerId;
    @NotBlank(message = "Beer name must not be blank")
    @Size(max = 255, message = "Beer name must be at most 255 characters")
    private String beerName;

    @NotNull(message = "Beer style is required")
    private BeerStyle beerStyle;

    @NotBlank(message = "UPC must not be blank")
    @Size(max = 50, message = "UPC must be at most 50 characters")
    private String upc;

    @PositiveOrZero(message = "Quantity on hand must be zero or positive")
    private Integer quantityOnHand;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal price;
    /* =========================
        AUDITING
        ========================= */
    private Instant createdDate;

    private Instant lastModifiedDate;

    /* =========================
       OPTIMISTIC LOCKING (information only)
       Used for optimistic locking (copied from entity, enforced by JPA)
       ========================= */
    private Long version;

}
