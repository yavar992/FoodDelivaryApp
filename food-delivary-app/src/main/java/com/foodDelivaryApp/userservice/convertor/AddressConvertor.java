package com.foodDelivaryApp.userservice.convertor;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.foodDelivaryApp.userservice.dto.AddressDTO;
import com.foodDelivaryApp.userservice.entity.Address;

@Mapper
public interface AddressConvertor {

    AddressConvertor MAPPER = Mappers.getMapper(AddressConvertor.class);

    @Mappings({
        @Mapping(target = "isDeleted", constant = "false")
    })
    @BeanMapping(ignoreByDefault = true)
    Address toAddress(AddressDTO dto);
}
