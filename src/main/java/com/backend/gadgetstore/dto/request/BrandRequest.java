package com.backend.gadgetstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BrandRequest {

    @NotBlank(message = "Brand name is required!")
    @Size(max = 100, message = "Brand name must be at most 100 characters!")
    private String name;

    @NotBlank(message = "Country is required!")
    @Size(max = 100, message = "Country must be at most 100 characters!")
    private String country;
}
