package com.backend.gadgetstore.mapper;

import com.backend.gadgetstore.dto.request.ProductRequest;
import com.backend.gadgetstore.dto.result.ProductResult;
import com.backend.gadgetstore.entity.Brand;
import com.backend.gadgetstore.entity.Category;
import com.backend.gadgetstore.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request, Brand brand, Category category){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .brand(brand)
                .build();
    }

    public ProductResult toResult(Product product){
        return new ProductResult(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                new ProductResult.Summary(product.getCategory().getId(), product.getCategory().getName()),
                new ProductResult.Summary(product.getBrand().getId(), product.getBrand().getName()),
                product.getCreatedAt()
        );
    }
}
