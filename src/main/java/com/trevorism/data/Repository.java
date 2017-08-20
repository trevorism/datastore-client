package com.trevorism.data;

import java.util.List;

/**
 * @author tbrooks
 */
public interface Repository<T> {

    List<T> list();
    T get(String id);
    T create(T itemToCreate);
    T update(String id, T itemToUpdate);
    T delete(String id);
}
