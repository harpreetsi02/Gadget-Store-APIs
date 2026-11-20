package com.backend.gadgetstore.mapper;

import com.backend.gadgetstore.dto.request.CategoryRequest;
import com.backend.gadgetstore.dto.response.CategoryResponse;
import com.backend.gadgetstore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request){

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(Category category){

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
