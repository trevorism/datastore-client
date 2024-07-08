package com.trevorism.data.model.sorting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SortBuilder {

    private final List<Sort> sorts = new LinkedList<>();

    public SortBuilder addSort(Sort simpleSort){
        sorts.add(simpleSort);
        return this;
    }

    public SortBuilder addSort(Sort... simpleSorts){
        sorts.addAll(Arrays.asList(simpleSorts));
        return this;
    }

    public ComplexSort build(){
        ComplexSort complexSort = new ComplexSort();
        sorts.forEach(complexSort::addSort);
        return complexSort;
    }
}
