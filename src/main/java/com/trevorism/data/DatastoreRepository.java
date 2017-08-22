package com.trevorism.data;

import com.google.gson.Gson;
import com.trevorism.http.HttpClient;
import com.trevorism.http.JsonHttpClient;

import java.util.List;

/**
 * @author tbrooks
 */
public class DatastoreRepository<T> implements Repository<T> {

    static final String DATASTORE_BASE_URL = "http://datastore.trevorism.com";
    private static final long DEFAULT_TIMEOUT_MILLIS = 10000;

    private final Class<T> clazz;
    private final String type;
    private final Gson gson = new Gson();
    private final HttpClient client = new JsonHttpClient();

    private final long pingTimeout;

    public DatastoreRepository(Class<T> clazz) {
        this(clazz, DEFAULT_TIMEOUT_MILLIS);
    }

    public DatastoreRepository(Class<T> clazz, long pingTimeout) {
        this.clazz = clazz;
        this.type = clazz.getSimpleName().toLowerCase();
        this.pingTimeout = pingTimeout;
    }

    @Override
    public List<T> list() {
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = client.get(url);
        return gson.fromJson(json, new ListType<>(clazz));

    }

    @Override
    public T get(String id) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = client.get(url);
        return gson.fromJson(json, clazz);
    }

    @Override
    public T create(T itemToCreate) {
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = gson.toJson(itemToCreate);
        String resultJson = client.post(url, json);
        return gson.fromJson(resultJson, clazz);
    }

    /**
     * Update will ignore nulls and changes to the id
     * @param id The id of the object being updated.
     * @param itemToUpdate The new object.
     * @return The updated object
     */
    @Override
    public T update(String id, T itemToUpdate) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = gson.toJson(itemToUpdate);
        String resultJson = client.put(url, json);
        return gson.fromJson(resultJson, clazz);
    }

    @Override
    public T delete(String id) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = client.delete(url);
        return gson.fromJson(json, clazz);
    }

    @Override
    public void ping() {
        try {
            //ping the API to wake it up since it is not always on
            String pong = client.get(DATASTORE_BASE_URL + "/ping");
            if (!"pong".equals(pong))
                throw new Exception("Unable to ping events");
        } catch (Exception e) {
            try {
                Thread.sleep(pingTimeout);
                String pong = client.get(DATASTORE_BASE_URL + "/ping");
                if (!"pong".equals(pong))
                    throw new RuntimeException("Unable to ping events after 10 second retry");
            } catch (InterruptedException ie) {
                throw new RuntimeException("Interrupted failure", ie);
            }
        }
    }
}
