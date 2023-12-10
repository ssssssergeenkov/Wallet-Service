package com.ivan.wallet.service;

import com.ivan.wallet.domain.Player;

/**
 * The SecurityService interface provides methods for registering and authorizing players.
 */
public interface SecurityService {

    /**
     * Registers a new player.
     *
     * @param username the username of the player
     * @param password the password of the player
     * @return the Player object representing the registered player
     */
    Player registration(String username, String password);

    /**
     * Authorizes a player.
     *
     * @param username the username of the player
     * @param password the password of the player
     * @return the Player object representing the authorized player
     */
    Player authorization(String username, String password);
}