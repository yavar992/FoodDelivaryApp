package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.DeliveryGuyProfileDTO;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryGuyMapper {

    DeliveryGuyMapper INSTANCE = Mappers.getMapper(DeliveryGuyMapper.class);

    DeliveryGuyProfileDTO deliveryGuyToDeliveryGuyProfileDTO(DeliveryGuy deliveryGuy);

    void updateDeliveryGuyFromDTO(DeliveryGuyProfileDTO deliveryGuyProfileDTO , DeliveryGuy deliveryGuy);

}
