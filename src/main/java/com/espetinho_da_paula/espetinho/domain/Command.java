package com.espetinho_da_paula.espetinho.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tb_command")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int table;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    private BigDecimal totalValue;

    private LocalDateTime createdAt;
}
