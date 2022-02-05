package com.trevorism.data.model.sorting;

public final class Sort {
    private final String field;
    private final boolean descending;

    public Sort(String field, boolean descending) {
        this.field = field;
        this.descending = descending;
    }

    public String getField() {
        return field;
    }

    public boolean isDescending() {
        return descending;
    }
}
