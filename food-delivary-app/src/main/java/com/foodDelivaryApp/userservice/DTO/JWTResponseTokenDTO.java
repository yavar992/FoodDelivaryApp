package com.foodDelivaryApp.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponseTokenDTO {

    private String accessToken;
    private String refreshToken;

}
