package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDelivaryApp.userservice.Enums.OfferTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicUpdate
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Use Long or Integer, depending on your needs

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


//    private boolean isActive;


}
