package com.ivan.wallet.dao;

import java.util.List;
import java.util.Optional;

public interface PlayersDao<K, E> {//ключ - K(key), сущность E(entity)

    Optional<E> findByName(K name);

    List<E> findAll();

    void update(E entity);

    E save(E entity);

    boolean delete(K name);
}