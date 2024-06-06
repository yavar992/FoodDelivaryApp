package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@DynamicUpdate
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String flat;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String area;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String town;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String city;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String state;

    @NotNull
    @NotBlank
    @Size(max = 7)
    private String pin;

    private String landmark;

    private boolean isDeleted;

    private boolean defaultAddress;
    private final boolean enabled = true;

//    private boolean isDefault;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToOne
    private OrderConfirmationDetails orderConfirmationDetails;
}
