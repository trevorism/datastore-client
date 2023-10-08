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

    List<T> all();
    List<T> list();

    T get(String id);

    T create(T itemToCreate);

    T update(String id, T itemToUpdate);

    T delete(String id);

    void ping();

    List<T> filter(ComplexFilter filter);
    List<T> page(PageRequest page);
    List<T> sort(ComplexSort sort);
}
