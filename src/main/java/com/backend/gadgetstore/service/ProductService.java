package com.backend.gadgetstore.service;

import com.backend.gadgetstore.dto.request.ProductRequest;
import com.backend.gadgetstore.dto.result.ProductResult;
import com.backend.gadgetstore.entity.Brand;
import com.backend.gadgetstore.entity.Category;
import com.backend.gadgetstore.entity.Product;
import com.backend.gadgetstore.exception.ResourceNotFoundException;
import com.backend.gadgetstore.mapper.ProductMapper;
import com.backend.gadgetstore.repository.BrandRepository;
import com.backend.gadgetstore.repository.CategoryRepository;
import com.backend.gadgetstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResult create(ProductRequest request){
        Category category = findCategory(request.getCategoryId());
        Brand brand = findBrand(request.getBrandId());

        Product product = productMapper.toEntity(request, brand, category);
        Product savedProduct = productRepository.save(product);
        log.info("Product created with ID {}", savedProduct.getId());
        return productMapper.toResult(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResult> getAll(){
        return productRepository.findAllWithDetails()
                .stream()
                .map(productMapper::toResult)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResult findById(Long productId){
        Product product = productRepository.findByIdWithDetails(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        return productMapper.toResult(product);
    }

    @Transactional
    public ProductResult update(Long productId, ProductRequest request){
        Product product = findOrThrow(productId);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(findCategory(request.getCategoryId()));
        product.setBrand(findBrand(request.getBrandId()));
        return productMapper.toResult(product);
    }

    @Transactional
    public void delete(Long productId){
        productRepository.delete(findOrThrow(productId));
    }

    private Category findCategory(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
    }

    private Brand findBrand(Long brandId){
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", brandId));
    }

    private Product findOrThrow(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));
    }
}
