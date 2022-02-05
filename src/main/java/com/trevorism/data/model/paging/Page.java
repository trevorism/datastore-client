package com.trevorism.data.model.paging;

public final class Page implements PageRequest{
    private final int page;
    private final int pageSize;

    public Page(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

}
