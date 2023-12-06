package com.ivan.wallet.dao;

import java.util.List;

public interface TransactionsDao<K, E> {//ключ - K(key), сущность E(entity)

    List<E> findByName(K name);

    E save(E entity);
}