package com.anaflavia.DSCommerce.service;


import com.anaflavia.DSCommerce.dto.ProductDTO;
import com.anaflavia.DSCommerce.entities.Product;
import com.anaflavia.DSCommerce.repositories.ProductRepository;
import com.anaflavia.DSCommerce.service.exceptions.DatabaseException;
import com.anaflavia.DSCommerce.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Product product = result.orElseThrow(
                () -> new ResourceNotFoundException("Recurso não Encontrado"));
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
          copyDtoToEntity(dto,entity);
          entity = repository.save(entity);
          return new ProductDTO(entity);
    }

    @Transactional
    public  ProductDTO update(Long id,ProductDTO dto){
        try {
        Product entity = repository.getReferenceById(id);
        copyDtoToEntity(dto,entity);
        return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e) {
         throw new  ResourceNotFoundException("Recurso não encontrado");
        }
    }

    public void deleteById(Long id) throws DatabaseException {

        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
