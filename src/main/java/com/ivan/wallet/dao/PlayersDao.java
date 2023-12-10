package com.ivan.wallet.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The PlayersDao interface provides methods for retrieving, saving, updating, and deleting player entities.
 *
 * @param <K> the type of the key used for querying players
 * @param <E> the type of the player entity
 */
public interface PlayersDao<K, E> {//ключ - K(key), сущность E(entity)

    /**
     * Retrieves a player by name.
     *
     * @param name the name used to query the player
     * @return an Optional containing the player entity if found, or an empty Optional if not found
     */
    Optional<E> findByName(K name);

    /**
     * Retrieves all players.
     *
     * @return a list of all player entities
     */
    List<E> findAll();

    /**
     * Saves a player entity.
     *
     * @param entity the player entity to be saved
     * @return the saved player entity
     */
    E save(E entity);

    /**
     * Deletes a player by name.
     *
     * @param name the name of the player to be deleted
     */
    void delete(K name);

    /**
     * Updates the balance of a player.
     *
     * @param name    the name of the player
     * @param balance the new balance of the player
     */
    void updatePlayerBalance(K name, BigDecimal balance);
}