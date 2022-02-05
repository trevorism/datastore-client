package com.trevorism.data.model.paging;

public final class Limit implements PageRequest {

    private final int limit;

    public Limit(int limit) {
        this.limit = limit;
    }
}
