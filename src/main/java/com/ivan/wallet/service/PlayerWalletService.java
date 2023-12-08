package com.ivan.wallet.service;

import java.math.BigDecimal;

/**
 * The PlayerWalletService interface represents a service for player registration,
 * authorization, and balance retrieval.
 */
public interface PlayerWalletService {

    /**
     * Retrieves the current balance of the player with the specified username.
     *
     * @param username The username of the player.
     * @return The current balance of the player as a BigDecimal.
     */
    BigDecimal currentPlayerBalance(String username);

    void updateBalance(String name, BigDecimal balance);

    void deleteAccount(String userName);
}