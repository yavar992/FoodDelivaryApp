package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class ItemTrackingDetailDTO {

    private Long itemId;
    private String productName;
    private Double price;
    private Integer quantity;
    private List<Byte[]> images;
}
