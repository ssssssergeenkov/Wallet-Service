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
    /**
     * The id field represents the player ID
     */
    private Integer id;

    /**
     * The name field represents the player's name.
     */
    private String name;

    /**
     * The password field represents the player's password.
     */
    private String password;

    /**
     * The balance field represents the player's balance.
     * Defaults to zero.
     */
    private BigDecimal balance = BigDecimal.ZERO;
}