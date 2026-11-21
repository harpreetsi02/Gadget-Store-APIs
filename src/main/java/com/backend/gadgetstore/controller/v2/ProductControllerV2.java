package com.backend.gadgetstore.controller.v2;

import com.backend.gadgetstore.dto.request.ProductRequest;
import com.backend.gadgetstore.dto.response.v2.ProductResponseV2;
import com.backend.gadgetstore.mapper.ProductV2Mapper;
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
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
@Tag(name = "Product (v2)", description = "Product APIs - v2 response shape")
public class ProductControllerV2 {

    private final ProductService productService;
    private final ProductV2Mapper productV2Mapper;

    @Operation(summary = "Create a product")
    @PostMapping
    public ResponseEntity<ProductResponseV2> create(
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponseV2 responseV2 =
                productV2Mapper.toResponse(productService.create(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseV2);
    }

    @Operation(summary = "List all products")
    @GetMapping
    public ResponseEntity<List<ProductResponseV2>> getAll(){
        List<ProductResponseV2> responseV2s =
                productService.getAll().stream()
                        .map(productV2Mapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responseV2s);
    }

    @Operation(summary = "Get a product")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseV2> getById(
            @PathVariable Long productId
    ) {
        ProductResponseV2 responseV2 =
                productV2Mapper.toResponse(productService.findById(productId));

        return ResponseEntity.ok(responseV2);
    }

    @Operation(summary = "Update a product")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseV2> update(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponseV2 responseV2 =
                productV2Mapper.toResponse(productService.update(productId, request));

        return ResponseEntity.ok(responseV2);
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long productId
    ) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
