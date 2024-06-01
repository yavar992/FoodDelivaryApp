package com.foodDelivaryApp.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ReviewAndRatingResponseDTO {

    private String customerName;
    private String customerEmail;
    private String review;
    private Integer rating;
}
