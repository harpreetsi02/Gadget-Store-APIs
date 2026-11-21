package com.backend.gadgetstore.repository;

import com.backend.gadgetstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCategory_Id(Long categoryId);
    boolean existsByBrand_Id(Long brandId);

    @Query("select p from Product p join fetch p.brand join fetch p.category")
    List<Product> findAllWithDetails();

    @Query("select p from Product p join fetch p.brand join fetch p.category where p.id = :id")
    Optional<Product> findByIdWithDetails(@Param("id") Long id);
}
