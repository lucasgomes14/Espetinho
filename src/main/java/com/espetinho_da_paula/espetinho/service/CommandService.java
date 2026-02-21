package com.espetinho_da_paula.espetinho.service;

import com.espetinho_da_paula.espetinho.domain.Command;
import com.espetinho_da_paula.espetinho.domain.CommandStatus;
import com.espetinho_da_paula.espetinho.domain.OrderItem;
import com.espetinho_da_paula.espetinho.domain.Product;
import com.espetinho_da_paula.espetinho.dto.CommandRequestDTO;
import com.espetinho_da_paula.espetinho.dto.OrderItemRequestDTO;
import com.espetinho_da_paula.espetinho.repository.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @Transactional
    public Command create(CommandRequestDTO dto) {
        Command command = new Command();
        command.setTableNumber(dto.tableNumber());
        command.setStatus(CommandStatus.OPEN);
        BigDecimal totalValue = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.items()) {
            OrderItem orderItem = orderItemService.buildItem(itemDTO);
            orderItem.setCommand(command);

            command.getOrderItems().add(orderItem);

            BigDecimal subTotal = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.quantity()));
            totalValue = totalValue.add(subTotal);
        }

        command.setTotalValue(totalValue);

        return commandRepository.save(command);
    }

    @Transactional
    public Command addItem(Long id, OrderItemRequestDTO dto) {
        Command command = findById(id);
        Product product = productService.findById(dto.productId());

        Optional<OrderItem> existingItemOpt = command.getOrderItems()
                .stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), product.getId()))
                .findFirst();

        if (existingItemOpt.isEmpty()) {
            OrderItem newItem = orderItemService.buildItem(dto);
            newItem.setCommand(command);

            command.getOrderItems().add(newItem);

            BigDecimal subTotal = newItem.getUnitPrice().multiply(BigDecimal.valueOf(dto.quantity()));
            command.setTotalValue(command.getTotalValue().add(subTotal));

            return commandRepository.save(command);
        }

        OrderItem existingItem = existingItemOpt.get();

        existingItem.setQuantity(existingItem.getQuantity() + dto.quantity());

        BigDecimal subTotal = existingItem.getUnitPrice().multiply(BigDecimal.valueOf(dto.quantity()));
        command.setTotalValue(command.getTotalValue().add(subTotal));

        return commandRepository.save(command);
    }

    @Transactional
    public Command removeAllItems(Long commandId, Long orderItemId) {
        Command command = findById(commandId);

        OrderItem orderItem = orderItemService.findById(orderItemId);

        command.getOrderItems().remove(orderItem);

        BigDecimal subTotal = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        command.setTotalValue(command.getTotalValue().subtract(subTotal));

        return commandRepository.save(command);
    }

    @Transactional
    public Command removeUnitItem(Long commandId, Long itemId) {
        Command command = findById(commandId);

        OrderItem orderItem = orderItemService.findById(itemId);

        command.getOrderItems()
                .stream()
                .filter(item -> Objects.equals(item.getId(), itemId) && item.getQuantity() > 0)
                .forEach(quantity -> quantity.setQuantity(quantity.getQuantity() - 1));

        command.getOrderItems().removeIf(item -> item.getQuantity() == 0);

        command.setTotalValue(command.getTotalValue().subtract(orderItem.getUnitPrice()));

        return commandRepository.save(command);
    }

    @Transactional
    public Command addUnitItem(Long commandId, Long itemId) {
        Command command = findById(commandId);

        OrderItem orderItem = orderItemService.findById(itemId);

        command.getOrderItems()
                .stream()
                .filter(item -> Objects.equals(item.getId(), itemId) && item.getQuantity() > 0)
                .forEach(quantity -> quantity.setQuantity(quantity.getQuantity() + 1));

        command.setTotalValue(command.getTotalValue().add(orderItem.getUnitPrice()));

        return commandRepository.save(command);
    }

    public Command findById(Long commandId) {
        return commandRepository.findById(commandId).orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
    }

    public List<Command> findAll() {
        return commandRepository.findAll();
    }

    public Command updateTableNumber(Long commandId, int tableNumber) {
        Command command = findById(commandId);
        command.setTableNumber(tableNumber);

        return commandRepository.save(command);
    }

    public Command updateStatus(Long commandId) {
        Command command = findById(commandId);
        if (command.getStatus() == CommandStatus.OPEN) {
            command.setStatus(CommandStatus.CLOSED);
            return commandRepository.save(command);
        }

        command.setStatus(CommandStatus.OPEN);
        return commandRepository.save(command);
    }

    public void deleteCommand(Long commandId) {
        Command command = findById(commandId);
        commandRepository.delete(command);
    }
}
