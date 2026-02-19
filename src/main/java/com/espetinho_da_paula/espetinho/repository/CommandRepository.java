package com.espetinho_da_paula.espetinho.repository;

import com.espetinho_da_paula.espetinho.domain.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
}
