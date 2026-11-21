package com.backend.gadgetstore.dto.response.v2;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseV2(
        Long id,
        String name,
        String description,
        Price price,
        Integer stockQuantity,
        Reference category,
        Reference brand,
        LocalDateTime createdAt
) {
    public record Price(BigDecimal amount, String currency){}
    public record Reference(Long id, String name){}
}
