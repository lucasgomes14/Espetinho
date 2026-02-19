package com.espetinho_da_paula.espetinho.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        BigDecimal price
) {
}
