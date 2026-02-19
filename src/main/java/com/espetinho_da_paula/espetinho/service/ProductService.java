package com.espetinho_da_paula.espetinho.service;

import com.espetinho_da_paula.espetinho.domain.Product;
import com.espetinho_da_paula.espetinho.dto.ProductRequestDTO;
import com.espetinho_da_paula.espetinho.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());

        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product update(Long id, ProductRequestDTO dto) {
        Product product = findById(id);

        product.setName(dto.name());
        product.setPrice(dto.price());

        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
