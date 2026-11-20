package com.backend.gadgetstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest{

        @NotBlank(message = "Category name is required!")
        @Size(max = 100, message = "Name must be at most 100 characters!")
        private String name;

        @Size(max = 255, message = "Description must be at most 255 characters!")
        private String description;
}
