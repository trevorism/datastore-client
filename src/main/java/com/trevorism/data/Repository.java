package com.trevorism.data;

import com.trevorism.data.model.filtering.ComplexFilter;
import com.trevorism.data.model.paging.Page;
import com.trevorism.data.model.paging.PageRequest;
import com.trevorism.data.model.sorting.ComplexSort;

import java.util.List;

/**
 * @author tbrooks
 */
public interface Repository<T> {

    List<T> list();
    List<T> list(String correlationId);

    T get(String id);
    T get(String id, String correlationId);

    T create(T itemToCreate);
    T create(T itemToCreate, String correlationId);

    T update(String id, T itemToUpdate);
    T update(String id, T itemToUpdate, String correlationId);

    T delete(String id);
    T delete(String id, String correlationId);

    void ping();

    List<T> filter(ComplexFilter filter);
    List<T> page(PageRequest page);
    List<T> sort(ComplexSort sort);
}
