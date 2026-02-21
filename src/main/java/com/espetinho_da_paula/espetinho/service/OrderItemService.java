package com.espetinho_da_paula.espetinho.service;

import com.espetinho_da_paula.espetinho.domain.OrderItem;
import com.espetinho_da_paula.espetinho.domain.Product;
import com.espetinho_da_paula.espetinho.dto.OrderItemRequestDTO;
import com.espetinho_da_paula.espetinho.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public OrderItem buildItem(OrderItemRequestDTO dto) {
        OrderItem orderItem = new OrderItem();

        Product product = productService.findById(dto.productId());
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.quantity());
        orderItem.setUnitPrice(product.getPrice());

        return orderItem;
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public void removeAllItems(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }
}
