package com.backend.gadgetstore.mapper;

import com.backend.gadgetstore.dto.response.v2.ProductResponseV2;
import com.backend.gadgetstore.dto.result.ProductResult;
import org.springframework.stereotype.Component;

@Component
public class ProductV2Mapper {

    private static final String DEFAULT_CURRENCY = "INR";

    public ProductResponseV2 toResponse(ProductResult result){
        return new ProductResponseV2(
                result.id(),
                result.name(),
                result.description(),
                new ProductResponseV2.Price(result.price(), DEFAULT_CURRENCY),
                result.stockQuantity(),
                new ProductResponseV2.Reference(result.category().id(), result.category().name()),
                new ProductResponseV2.Reference(result.brand().id(), result.brand().name()),
                result.createdAt()
        );
    }
}
