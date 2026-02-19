package com.espetinho_da_paula.espetinho.repository;

import com.espetinho_da_paula.espetinho.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
