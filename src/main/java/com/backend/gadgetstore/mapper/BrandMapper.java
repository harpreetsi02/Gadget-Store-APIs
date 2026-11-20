package com.backend.gadgetstore.mapper;

import com.backend.gadgetstore.dto.request.BrandRequest;
import com.backend.gadgetstore.dto.response.BrandResponse;
import com.backend.gadgetstore.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequest request){

        return Brand.builder()
                .name(request.getName())
                .country(request.getCountry())
                .build();
    }

    public BrandResponse toResponse(Brand brand){

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .country(brand.getCountry())
                .build();
    }
}
