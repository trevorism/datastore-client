package com.trevorism.data.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * @author tbrooks
 */
public class GsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @Override
    public T deserializeJsonObject(String jsonObject, Class<T> clazz) {
        return gson.fromJson(jsonObject, clazz);
    }

    @Override
    public List<T> deserializeJsonArray(String jsonArray, Class<T> clazz) {
        return gson.fromJson(jsonArray, new ListType<>(clazz));
    }
}
