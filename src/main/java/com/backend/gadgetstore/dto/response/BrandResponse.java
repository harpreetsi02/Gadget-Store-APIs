package com.backend.gadgetstore.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponse {

    private Long id;
    private String name;
    private String country;
}
