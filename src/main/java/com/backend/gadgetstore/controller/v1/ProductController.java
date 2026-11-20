package com.backend.gadgetstore.controller.v1;

import com.backend.gadgetstore.dto.request.ProductRequest;
import com.backend.gadgetstore.dto.response.v1.ProductResponseV1;
import com.backend.gadgetstore.mapper.ProductV1Mapper;
import com.backend.gadgetstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product (v1)", description = "Product APIs - v1 response shape")
public class ProductController {

    private final ProductService productService;
    private final ProductV1Mapper productV1Mapper;

    @Operation(summary = "Create a product")
    @PostMapping
    public ResponseEntity<ProductResponseV1> create(
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponseV1 responseV1 =
                productV1Mapper.toResponse(productService.create(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseV1);
    }

    @Operation(summary = "List all products")
    @GetMapping
    public ResponseEntity<List<ProductResponseV1>> getAll() {
        List<ProductResponseV1> responseV1s = productService.getAll()
                .stream()
                .map(productV1Mapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseV1s);
    }

    @Operation(summary = "Get a product")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseV1> getById(
            @PathVariable Long id
    ) {
        ProductResponseV1 responseV1 =
                productV1Mapper.toResponse(productService.findById(id));

        return ResponseEntity.ok(responseV1);
    }

    @Operation(summary = "Update a product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseV1> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponseV1 responseV1 =
                productV1Mapper.toResponse(productService.update(id, request));

        return ResponseEntity.ok(responseV1);
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}