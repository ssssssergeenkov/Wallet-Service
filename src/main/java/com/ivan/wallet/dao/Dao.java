package com.ivan.wallet.dao;

/**
 * The Dao interface represents a generic data access object.
 *
 * @param <K> The key type.
 * @param <E> The entity type.
 */
public interface Dao<K, E> { //ключ - K(key), сущность E(entity)
    E save(E ticket);
}