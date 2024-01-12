package com.foodDelivaryApp.userservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {


    private String flat;

    private String area;

    private String town;

    private String city;

    private String state;

    private String pin;

    private String landmark;

    private boolean defaultAddress;


}
