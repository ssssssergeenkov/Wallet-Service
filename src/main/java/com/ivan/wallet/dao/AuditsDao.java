package com.ivan.wallet.dao;

import java.util.List;

/**
 * The AuditsDao interface provides methods for retrieving and saving audit records.
 *
 * @param <K> the type of the key used for querying audit records
 * @param <E> the type of the audit record entity
 */
public interface AuditsDao <K, E> {//ключ - K(key), сущность E(entity)

    /**
     * Retrieves audit records by name.
     *
     * @param name the name used to query audit records
     * @return a list of audit records matching the name
     */
    List<E> findByName(K name);

    /**
     * Saves an audit record.
     *
     * @param entity the audit record entity to be saved
     * @return the saved audit record entity
     */
    E save(E entity);
}