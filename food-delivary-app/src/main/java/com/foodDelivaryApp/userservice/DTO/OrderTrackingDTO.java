package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Data
public class OrderTrackingDTO {

    private Long orderId;
    private String orderStatus;
    private LocalDateTime estimatedDeliveryDate;
    private Double totalAmount;
    private String paymentStatus;
    private String shippingAddress;
    private String trackingNumber; // If applicable
    private String trackingLink;   // If applicable
    private List<ItemTrackingDetailDTO> itemDetailsList; // List of item details
}
