package com.trevorism.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trevorism.data.deserialize.DatastoreDeserializer;
import com.trevorism.data.deserialize.Deserializer;
import com.trevorism.data.exception.CreationFailedException;
import com.trevorism.data.exception.DataOperationException;
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
        String url = DATASTORE_BASE_URL + "/object/" + type;
        String json = safeHttpGet(url);
        return deserializer.deserializeJsonArray(json, clazz);
    }

    @Override
    public T get(String id) {
        String url = DATASTORE_BASE_URL + "/object/" + type + "/" + id;
        String resultJson = safeHttpGet(url);
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T create(T itemToCreate) {
        String url = DATASTORE_BASE_URL + "/object/" + type;
        String json = gson.toJson(itemToCreate);
        String resultJson = safeHttpPost(url, json);
        try {
            return deserializer.deserializeJsonObject(resultJson, clazz);
        } catch (Exception e) {
            throw new CreationFailedException(e);
        }
    }

    /**
     * Update will ignore nulls and changes to the id
     *
     * @param id           The id of the object being updated.
     * @param itemToUpdate The new object.
     * @return The updated object
     */
    @Override
    public T update(String id, T itemToUpdate) {
        String url = DATASTORE_BASE_URL + "/object/" + type + "/" + id;
        String json = gson.toJson(itemToUpdate);
        String resultJson = safeHttpPut(url, json);
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T delete(String id) {
        String url = DATASTORE_BASE_URL + "/object/" + type + "/" + id;
        String resultJson = safeHttpDelete(url);
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public void ping() {
        RequestUtils.ping(client);
    }

    @Override
    public List<T> filter(ComplexFilter filter) {
        String url = DATASTORE_BASE_URL + "/filter/" + type;
        String json = gson.toJson(filter);
        String resultJson = safeHttpPost(url, json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }

    @Override
    public List<T> page(PageRequest page) {
        String url = DATASTORE_BASE_URL + "/page/" + type;
        String json = gson.toJson(page);
        String resultJson = safeHttpPost(url, json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }

    @Override
    public List<T> sort(ComplexSort sort) {
        String url = DATASTORE_BASE_URL + "/sort/" + type;
        String json = gson.toJson(sort);
        String resultJson = safeHttpPost(url, json);
        return deserializer.deserializeJsonArray(resultJson, clazz);
    }

    private String safeHttpGet(String url) {
        try {
            return client.get(url);
        } catch (Exception e) {
            throw new DataOperationException("Unable to HTTP GET: " + url, e);
        }
    }

    private String safeHttpPost(String url, String json) {
        try {
            return client.post(url, json);
        } catch (Exception e) {
            throw new DataOperationException("Unable to HTTP POST: " + url, e);
        }
    }

    private String safeHttpPut(String url, String json) {
        try {
            return client.put(url, json);
        } catch (Exception e) {
            throw new DataOperationException("Unable to HTTP PUT: " + url, e);
        }
    }

    private String safeHttpDelete(String url) {
        try {
            return client.delete(url);
        } catch (Exception e) {
            throw new DataOperationException("Unable to HTTP DELETE: " + url, e);
        }
    }

}
