package com.trevorism.data.deserialize;

import java.util.List;

/**
 * @author tbrooks
 */
public interface Deserializer<T> {

    T deserializeJsonObject(String jsonObject, Class<T> clazz);
    List<T> deserializeJsonArray(String jsonArray, Class<T> clazz);
}
