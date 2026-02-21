package com.espetinho_da_paula.espetinho.dto;

import java.util.List;

public record CommandRequestDTO(
        int tableNumber,
        List<OrderItemRequestDTO> items
) {
}
