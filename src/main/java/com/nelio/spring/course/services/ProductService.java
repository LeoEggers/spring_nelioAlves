package com.nelio.spring.course.services;

import com.nelio.spring.course.entities.Product;
import com.nelio.spring.course.repositories.ProductRepository;
import com.nelio.spring.course.services.exceptions.DatabaseException;
import com.nelio.spring.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));
            productRepository.deleteById(product.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product updateProduct(Long id, Product newProduct) {
        try {
            Product existingProduct = productRepository.getReferenceById(id);
            copyProductData(existingProduct, newProduct);
            return productRepository.save(existingProduct);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyProductData(Product existingProduct, Product newProduct) {
        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setImgUrl(newProduct.getImgUrl());
    }
}
