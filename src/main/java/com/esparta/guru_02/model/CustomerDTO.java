package com.esparta.guru_02.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/*
 * Author: M
 * Date: 25-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private UUID customerId;
    @NotBlank(message = "Customer name must not be blank")
    @Size(max = 255, message = "Customer name must be at most 255 characters")
    private String customerName;
    /* =========================
    AUDITING
    ========================= */
    private Instant createdDate;

    private Instant lastModifiedDate;

    /* =========================
       OPTIMISTIC LOCKING
       Used for optimistic locking (copied from entity, enforced by JPA)
    ========================= */
    private Long version;

}
