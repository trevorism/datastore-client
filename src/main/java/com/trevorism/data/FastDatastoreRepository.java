package com.trevorism.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trevorism.data.deserialize.DatastoreDeserializer;
import com.trevorism.data.deserialize.Deserializer;
import com.trevorism.data.exception.CreationFailedException;
import com.trevorism.data.exception.IdMissingException;
import com.trevorism.data.exception.InvalidDataOperation;
import com.trevorism.data.model.filtering.ComplexFilter;
import com.trevorism.data.model.paging.PageRequest;
import com.trevorism.data.model.sorting.ComplexSort;
import com.trevorism.https.DefaultSecureHttpClient;
import com.trevorism.https.SecureHttpClient;

import java.util.List;

import static com.trevorism.data.RequestUtils.DATASTORE_BASE_URL;

/**
 * @author tbrooks
 */
public class FastDatastoreRepository<T> implements Repository<T> {

    private final Class<T> clazz;
    private final String type;
    private final Gson gson;
    private final Deserializer<T> deserializer;
    private final SecureHttpClient client;

    public FastDatastoreRepository(Class<T> clazz) {
        this(clazz, new DefaultSecureHttpClient());
    }

    public FastDatastoreRepository(Class<T> clazz, SecureHttpClient secureHttpClient) {
        this.clazz = clazz;
        this.type = clazz.getSimpleName().toLowerCase();
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        this.deserializer = new DatastoreDeserializer<>();
        this.client = secureHttpClient;
    }

    @Override
    public List<T> list() {
        return list(null);
    }

    @Override
    public List<T> list(String correlationId) {
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = client.get(url, correlationId);
        return deserializer.deserializeJsonArray(json, clazz);
    }

    @Override
    public T get(String id) {
        return get(id, null);
    }

    @Override
    public T get(String id, String correlationId) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String resultJson = client.get(url, correlationId);
        if (resultJson.startsWith("<html>"))
            throw new IdMissingException(id, correlationId);

        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T create(T itemToCreate) {
        return create(itemToCreate, null);
    }

    @Override
    public T create(T itemToCreate, String correlationId) {
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = gson.toJson(itemToCreate);
        String resultJson = client.post(url, json, correlationId);
        try {
            return deserializer.deserializeJsonObject(resultJson, clazz);
        } catch (Exception e) {
            throw new CreationFailedException(e);
        }
    }

    @Override
    public T update(String id, T itemToUpdate) {
        return update(id, itemToUpdate, null);
    }

    /**
     * Update will ignore nulls and changes to the id
     *
     * @param id           The id of the object being updated.
     * @param itemToUpdate The new object.
     * @return The updated object
     */
    @Override
    public T update(String id, T itemToUpdate, String correlationId) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = gson.toJson(itemToUpdate);
        String resultJson = client.put(url, json, correlationId);
        if (resultJson.startsWith("<html>") || resultJson.isEmpty())
            throw new IdMissingException(id, correlationId);
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T delete(String id) {
        return delete(id, null);
    }

    @Override
    public T delete(String id, String correlationId) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String resultJson = client.delete(url, correlationId);
        if (resultJson.startsWith("<html>") || resultJson.isEmpty())
            throw new IdMissingException(id, correlationId);
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public void ping() {
        RequestUtils.ping();
    }

    @Override
    public List<T> filter(ComplexFilter filter) {
        String url = DATASTORE_BASE_URL + "/filter/" + type;
        String json = gson.toJson(filter);
        String resultJson = client.post(url, json);
        if (resultJson.startsWith("<html>"))
            throw new InvalidDataOperation("Unable to complete this request to filter: " + json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }

    @Override
    public List<T> page(PageRequest page) {
        String url = DATASTORE_BASE_URL + "/page/" + type;
        String json = gson.toJson(page);
        String resultJson = client.post(url, json);
        if (resultJson.startsWith("<html>"))
            throw new InvalidDataOperation("Unable to complete this request to page: " + json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }

    @Override
    public List<T> sort(ComplexSort sort) {
        String url = DATASTORE_BASE_URL + "/sort/" + type;
        String json = gson.toJson(sort);
        String resultJson = client.post(url, json);
        if (resultJson.startsWith("<html>"))
            throw new InvalidDataOperation("Unable to complete this request to sort: " + json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }
}
