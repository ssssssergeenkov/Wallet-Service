package com.ivan.wallet.dao;

import java.util.List;

public interface Dao<K, E> { //ключ - K(key), сущность E(entity)
    E save(E ticket);
}