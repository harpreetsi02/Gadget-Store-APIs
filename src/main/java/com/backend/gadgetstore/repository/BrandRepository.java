package com.backend.gadgetstore.repository;

import com.backend.gadgetstore.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByNameIgnoreCase(String name);
}
