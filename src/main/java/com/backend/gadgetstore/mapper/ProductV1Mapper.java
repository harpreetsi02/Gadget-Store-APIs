package com.backend.gadgetstore.mapper;

import com.backend.gadgetstore.dto.response.v1.ProductResponseV1;
import com.backend.gadgetstore.dto.result.ProductResult;
import org.springframework.stereotype.Component;

@Component
public class ProductV1Mapper {

    public ProductResponseV1 toResponse(ProductResult result){
        return ProductResponseV1.builder()
                .id(result.id())
                .name(result.name())
                .description(result.description())
                .price(result.price())
                .stockQuantity(result.stockQuantity())
                .categoryName(result.category().name())
                .brandName(result.brand().name())
                .build();
    }
}
