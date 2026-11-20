package com.backend.gadgetstore.repository;

import com.backend.gadgetstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCategory_Id(Long categoryId);
    boolean existsByBrand_Id(Long brandId);
}
