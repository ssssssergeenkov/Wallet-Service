package com.ivan.wallet.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PlayersDao<K, E> {//ключ - K(key), сущность E(entity)

    Optional<E> findByName(K name);

    List<E> findAll();

    E save(E entity);

    void delete(K name);

    void updatePlayerBalance(K name, BigDecimal balance);
}