package com.foodDelivaryApp.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDetails {

    private String email;
    private Boolean isVerified;
}
