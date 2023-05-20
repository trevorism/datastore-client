package com.trevorism.data.model.filtering;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FilterBuilder {

    private final List<SimpleFilter> simpleFilterList = new LinkedList<>();

    public FilterBuilder(){}
    
    public FilterBuilder addFilter(SimpleFilter simpleFilter){
        simpleFilterList.add(simpleFilter);
        return this;
    }

    public FilterBuilder addFilter(SimpleFilter... simpleFilters){
        simpleFilterList.addAll(Arrays.asList(simpleFilters));
        return this;
    }

    public ComplexFilter build(){
        ComplexFilter complexFilter = new ComplexFilter();
        simpleFilterList.forEach(complexFilter::addSimpleFilter);
        return complexFilter;
    }
}
