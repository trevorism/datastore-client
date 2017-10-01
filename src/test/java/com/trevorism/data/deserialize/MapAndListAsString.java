package com.trevorism.data.deserialize;

import java.util.List;
import java.util.Map;

/**
 * @author tbrooks
 */
public class MapAndListAsString {

    private List<String> items;
    private Map<String,String> keyValue;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }
}
