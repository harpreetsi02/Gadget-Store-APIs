package com.backend.gadgetstore.service;

import com.backend.gadgetstore.dto.request.BrandRequest;
import com.backend.gadgetstore.dto.response.BrandResponse;
import com.backend.gadgetstore.entity.Brand;
import com.backend.gadgetstore.exception.DuplicateResourceException;
import com.backend.gadgetstore.exception.ResourceNotFoundException;
import com.backend.gadgetstore.mapper.BrandMapper;
import com.backend.gadgetstore.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandResponse create(BrandRequest request){
        if (brandRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateResourceException("Brand", request.getName());
        }

        Brand brand = brandMapper.toEntity(request);
        Brand savedBrand = brandRepository.save(brand);
        log.info("Brand created with ID {}", savedBrand.getId());

        return brandMapper.toResponse(savedBrand);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> findAll(){
        return brandRepository.findAll().stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BrandResponse findById(Long id){
        return brandMapper.toResponse(findOrThrow(id));
    }

    @Transactional
    public BrandResponse update(Long id, BrandRequest request){
        Brand brand = findOrThrow(id);
        if (!brand.getName().equalsIgnoreCase(request.getName()) &&
                brandRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateResourceException("Brand", request.getName());
        }

        brand.setName(request.getName());
        brand.setCountry(request.getCountry());

        return brandMapper.toResponse(brand);
    }

    @Transactional
    public void delete(Long id){
        brandRepository.delete(findOrThrow(id));
    }

    private Brand findOrThrow(Long id){
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", id));
    }
}
