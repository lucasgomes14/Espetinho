package com.espetinho_da_paula.espetinho.controller;

import com.espetinho_da_paula.espetinho.domain.Command;
import com.espetinho_da_paula.espetinho.dto.CommandRequestDTO;
import com.espetinho_da_paula.espetinho.dto.OrderItemRequestDTO;
import com.espetinho_da_paula.espetinho.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
public class CommandController {
    private final CommandService commandService;

    @PostMapping
    public ResponseEntity<Command> createCommand(@RequestBody CommandRequestDTO dto) {
        Command command = commandService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(command);
    }

    @PatchMapping("/addItem/{id}")
    public ResponseEntity<Command> addItem(@PathVariable Long id, @RequestBody OrderItemRequestDTO dto) {
        Command command = commandService.addItem(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @PatchMapping("/removeAllItems/{commandId}/{orderItemId}")
    public ResponseEntity<Command> removeAllItems(@PathVariable Long commandId, @PathVariable Long orderItemId) {
        Command command = commandService.removeAllItems(commandId, orderItemId);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @PatchMapping("/removeUnitItem/{commandId}/{itemId}")
    public ResponseEntity<Command> removeUnitItem(@PathVariable Long commandId, @PathVariable Long itemId) {
        Command command = commandService.removeUnitItem(commandId, itemId);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @PatchMapping("/addUnitItem/{commandId}/{itemId}")
    public ResponseEntity<Command> addUnitItem(@PathVariable Long commandId, @PathVariable Long itemId) {
        Command command = commandService.addUnitItem(commandId, itemId);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommand(@PathVariable Long id) {
        Command command = commandService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @GetMapping
    public ResponseEntity<List<Command>> getAllCommand() {
        List<Command> commands = commandService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(commands);
    }

    @PatchMapping("/tableNumber/{commandId}/{tableNumber}")
    public ResponseEntity<Command> updateTableNumber(@PathVariable Long commandId, @PathVariable int tableNumber) {
        Command command = commandService.updateTableNumber(commandId, tableNumber);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @PatchMapping("/status/{commandId}")
    public ResponseEntity<Command> updateStatus(@PathVariable Long commandId) {
        Command command = commandService.updateStatus(commandId);

        return ResponseEntity.status(HttpStatus.OK).body(command);
    }

    @DeleteMapping("/{commandId}")
    public ResponseEntity<Command> deleteCommand(@PathVariable Long commandId) {
        commandService.deleteCommand(commandId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
