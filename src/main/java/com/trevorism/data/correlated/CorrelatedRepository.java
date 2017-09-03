package com.trevorism.data.correlated;

import java.util.List;

/**
 * @author tbrooks
 */
public interface CorrelatedRepository<T> {

    List<T> list(String correlationId);
    T get(String id, String correlationId);
    T create(T itemToCreate, String correlationId);
    T update(String id, T itemToUpdate, String correlationId);
    T delete(String id, String correlationId);
    void ping();
}
