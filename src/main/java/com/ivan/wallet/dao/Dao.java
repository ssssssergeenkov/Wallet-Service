package com.ivan.wallet.dao;

public interface Dao<K, E> { //ключ - K(key), сущность E(entity)
    E save(E ticket);
}