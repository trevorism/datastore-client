package com.trevorism.data.model.sorting;

import com.trevorism.data.model.filtering.SimpleFilter;

import java.util.LinkedList;
import java.util.List;

public class ComplexSort {
    private final List<Sort> sorts = new LinkedList<>();

    public List<Sort> getSorts() {
        return sorts;
    }

    public void addSort(Sort sort) {
        this.sorts.add(sort);
    }
}
