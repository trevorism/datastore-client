package com.trevorism.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trevorism.data.deserialize.DatastoreDeserializer;
import com.trevorism.data.deserialize.Deserializer;
import com.trevorism.http.headers.HeadersHttpClient;
import com.trevorism.http.headers.HeadersJsonHttpClient;
import com.trevorism.http.util.ResponseUtils;
import com.trevorism.secure.PasswordProvider;

import java.util.List;
import java.util.Map;

import static com.trevorism.data.RequestUtils.DATASTORE_BASE_URL;

/**
 * @author tbrooks
 */
public class FastDatastoreRepository<T> implements Repository<T> {


    private final Class<T> clazz;
    private final String type;
    private final Gson gson;
    private final Deserializer<T> deserializer;
    private final HeadersHttpClient client;
    private final PasswordProvider passwordProvider;

    private final long pingTimeout;

    public FastDatastoreRepository(Class<T> clazz) {
        this(clazz, DEFAULT_TIMEOUT_MILLIS);
    }

    public FastDatastoreRepository(Class<T> clazz, long pingTimeout) {
        this.clazz = clazz;
        this.type = clazz.getSimpleName().toLowerCase();
        this.pingTimeout = pingTimeout;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        this.deserializer = new DatastoreDeserializer<>();
        this.client = new HeadersJsonHttpClient();
        this.passwordProvider = new PasswordProvider();
    }

    @Override
    public List<T> list() {
        return list(null);
    }

    @Override
    public List<T> list(String correlationId) {
        Map<String, String> headersMap = RequestUtils.createHeaderMap(passwordProvider, correlationId);
        String url =  DATASTORE_BASE_URL + "/api/" + type;
        String json = ResponseUtils.getEntity(client.get(url, headersMap));
        return deserializer.deserializeJsonArray(json, clazz);
    }

    @Override
    public T get(String id) {
        return get(id, null);
    }

    @Override
    public T get(String id, String correlationId) {
        Map<String, String> headersMap = RequestUtils.createHeaderMap(passwordProvider, correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String resultJson = ResponseUtils.getEntity(client.get(url, headersMap));
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T create(T itemToCreate) {
        return create(itemToCreate, null);
    }

    @Override
    public T create(T itemToCreate, String correlationId) {
        Map<String, String> headersMap = RequestUtils.createHeaderMap(passwordProvider, correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = gson.toJson(itemToCreate);
        String resultJson = ResponseUtils.getEntity(client.post(url, json, headersMap));
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T update(String id, T itemToUpdate) {
        return update(id, itemToUpdate, null);
    }

    /**
     * Update will ignore nulls and changes to the id
     * @param id The id of the object being updated.
     * @param itemToUpdate The new object.
     * @return The updated object
     */
    @Override
    public T update(String id, T itemToUpdate, String correlationId) {
        Map<String, String> headersMap = RequestUtils.createHeaderMap(passwordProvider, correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = gson.toJson(itemToUpdate);
        String resultJson = ResponseUtils.getEntity(client.put(url, json, headersMap));
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public T delete(String id) {
        return delete(id, null);
    }

    @Override
    public T delete(String id, String correlationId) {
        Map<String, String> headersMap = RequestUtils.createHeaderMap(passwordProvider, correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String resultJson = ResponseUtils.getEntity(client.get(url, headersMap));
        return deserializer.deserializeJsonObject(resultJson, clazz);
    }

    @Override
    public void ping() {
        RequestUtils.ping(pingTimeout);
    }
}
