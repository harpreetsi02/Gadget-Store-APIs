package com.backend.gadgetstore.controller.v1;

import com.backend.gadgetstore.dto.request.BrandRequest;
import com.backend.gadgetstore.dto.response.BrandResponse;
import com.backend.gadgetstore.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "Brand management APIs")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "Create a brand")
    @PostMapping
    public ResponseEntity<BrandResponse> create(
            @Valid @RequestBody BrandRequest request
    ) {
        BrandResponse response =
                brandService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List all brands")
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAll(){
        List<BrandResponse> responses =
                brandService.findAll();

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a brand")
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> findById(
            @PathVariable Long id
    ) {
        BrandResponse response =
                brandService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a brand")
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequest request
    ) {
        BrandResponse response =
                brandService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a brand")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
