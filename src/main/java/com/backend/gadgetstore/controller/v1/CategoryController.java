package com.backend.gadgetstore.controller.v1;

import com.backend.gadgetstore.dto.request.CategoryRequest;
import com.backend.gadgetstore.dto.response.CategoryResponse;
import com.backend.gadgetstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories (v1)")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a category")
    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest request
    ) {
        CategoryResponse response =
                categoryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List all categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> responses =
                categoryService.getAll();

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a category")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(
            @PathVariable Long id
    ) {
        CategoryResponse response =
                categoryService.getById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        CategoryResponse response =
                categoryService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}