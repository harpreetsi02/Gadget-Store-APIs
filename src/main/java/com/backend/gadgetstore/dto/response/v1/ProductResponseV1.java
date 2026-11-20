package com.backend.gadgetstore.dto.response.v1;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductResponseV1 {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;               // plain number
    private Integer stockQuantity;
    private String brandName;               // flat String
    private String categoryName;            // flat String
}
