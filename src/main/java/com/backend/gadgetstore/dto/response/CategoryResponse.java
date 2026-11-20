package com.backend.gadgetstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse{
        private Long id;
        private String name;
        private String description;
}
