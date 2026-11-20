package com.backend.gadgetstore.dto.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResult(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Summary category,
        Summary brand,
        LocalDateTime createdAt
) {
    public record Summary(Long id, String name) {}
}
