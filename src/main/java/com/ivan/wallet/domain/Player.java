package com.ivan.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The Player class represents a player entity.
 * It contains information about a player, including their ID, name, password, and balance.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private String name;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
}