package com.anaflavia.DSCommerce.service;


import com.anaflavia.DSCommerce.dto.ProductDTO;
import com.anaflavia.DSCommerce.entities.Product;
import com.anaflavia.DSCommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product);
        return dto;
//        Product product = repository.findById(id).get();
//        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable peageble){
        Page<Product> result = repository.findAll(peageble);
        return  result.map(ProductDTO::new);
    }
    @Transactional
    public ProductDTO insert(ProductDTO dto){
          Product entity = new Product();
          entity.setName(dto.getName());
          entity.setDescription(dto.getDescription());
          entity.setPrice(dto.getPrice());
          entity.setImgUrl(dto.getImgUrl());

          entity = repository.save(entity);

          return new ProductDTO(entity);
    }
}
