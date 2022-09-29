package com.trevorism.data.model.sorting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SortBuilder {

    private final List<Sort> sorts = new LinkedList<>();

    public SortBuilder addSort(Sort simpleFilter){
        sorts.add(simpleFilter);
        return this;
    }

    public SortBuilder addSort(Sort... simpleFilters){
        sorts.addAll(Arrays.asList(simpleFilters));
        return this;
    }

    public ComplexSort build(){
        ComplexSort complexSort = new ComplexSort();
        sorts.forEach(complexSort::addSort);
        return complexSort;
    }
}
