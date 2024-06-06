package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.DeliveryGuyRatingDTO;
import com.foodDelivaryApp.userservice.entity.DeliveryGuyRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryGuyReviewAndRatingMapper {

        DeliveryGuyReviewAndRatingMapper  INSTANCE = Mappers.getMapper(DeliveryGuyReviewAndRatingMapper.class);

        DeliveryGuyRating convertDeliveryGuyRatingDTOToDeliveryGuyRating(DeliveryGuyRatingDTO deliveryGuyRatingDTO);

}
