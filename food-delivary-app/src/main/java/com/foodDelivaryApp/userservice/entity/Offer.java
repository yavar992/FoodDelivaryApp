package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
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
    private String offerType;
    private String description;
    private double offerPercentage;  // Use double or BigDecimal
    private Date expireDate;
    private boolean isActive;


}
