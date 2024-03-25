package com.foodDelivaryApp.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foodDelivaryApp.userservice.entity.OfferTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OfferDTO {
    @Enumerated(EnumType.STRING)
    private OfferTypeEnum offerType;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private double offerPercentage;  // Use double or BigDecimal

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expireDate;

}
