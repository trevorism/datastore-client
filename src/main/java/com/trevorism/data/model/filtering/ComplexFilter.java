package com.trevorism.data.model.filtering;

import java.util.LinkedList;
import java.util.List;

public class ComplexFilter {

    private final String type = "and";
    private final List<SimpleFilter> simpleFilters = new LinkedList<>();

    public List<SimpleFilter> getSimpleFilters() {
        return simpleFilters;
    }

    public void addSimpleFilter(SimpleFilter simpleFilter) {
        this.simpleFilters.add(simpleFilter);
    }

}
