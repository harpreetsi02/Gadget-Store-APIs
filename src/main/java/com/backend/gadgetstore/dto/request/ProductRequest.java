package com.backend.gadgetstore.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required!")
    @Size(max = 150, message = "Product name must be at most 150 characters!")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters!")
    private String description;

    @NotNull(message = "Price is required!")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0!")
    @Digits(integer = 8, fraction = 2, message = "Price format is invalid!")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required!")
    @Min(value = 0, message = "Stock cannot be negative!")
    private Integer stockQuantity;

    @NotNull(message = "Category ID is required!")
    private Long categoryId;

    @NotNull(message = "Brand ID is required!")
    private Long brandId;
}
