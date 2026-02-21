package com.espetinho_da_paula.espetinho.dto;

public record OrderItemRequestDTO(
        Long productId,
        int quantity
) {
}
