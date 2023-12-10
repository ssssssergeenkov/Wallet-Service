package com.ivan.wallet.dao;

import java.util.List;

/**
 * The TransactionsDao interface provides methods for retrieving and saving transactions.
 *
 * @param <K> the type of the key used for querying transactions
 * @param <E> the type of the transaction entity
 */
public interface TransactionsDao<K, E> {//ключ - K(key), сущность E(entity)

    /**
     * Retrieves transactions by name.
     *
     * @param name the name used to query transactions
     * @return a list of transactions matching the name
     */
    List<E> findByName(K name);

    /**
     * Saves a transaction.
     *
     * @param entity the transaction entity to be saved
     * @return the saved transaction entity
     */
    E save(E entity);
}