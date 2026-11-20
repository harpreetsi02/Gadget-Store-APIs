package com.backend.gadgetstore.service;

import com.backend.gadgetstore.dto.request.CategoryRequest;
import com.backend.gadgetstore.dto.response.CategoryResponse;
import com.backend.gadgetstore.entity.Category;
import com.backend.gadgetstore.exception.DuplicateResourceException;
import com.backend.gadgetstore.exception.ResourceNotFoundException;
import com.backend.gadgetstore.maper.CategoryMapper;
import com.backend.gadgetstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse create(CategoryRequest request){
        if (categoryRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateResourceException("Category", request.getName());
        }

        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created with ID {}", savedCategory.getId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id){
        return categoryMapper.toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request){
        Category category = findOrThrow(id);
        if (!category.getName().equalsIgnoreCase(request.getName()) &&
                categoryRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateResourceException("Category", request.getName());
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void delete(Long id){
        categoryRepository.delete(findOrThrow(id));
    }

    private Category findOrThrow(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
