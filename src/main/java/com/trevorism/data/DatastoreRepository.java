package com.trevorism.data;

import com.google.gson.Gson;
import com.trevorism.http.HttpClient;
import com.trevorism.http.JsonHttpClient;
import com.trevorism.http.headers.HeadersHttpClient;
import com.trevorism.http.headers.HeadersJsonHttpClient;
import com.trevorism.http.util.ResponseUtils;
import com.trevorism.secure.PasswordProvider;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.List;

import static com.trevorism.data.RequestUtils.DATASTORE_BASE_URL;

/**
 * @author tbrooks
 */
public class DatastoreRepository<T> implements Repository<T> {

    private static final long DEFAULT_TIMEOUT_MILLIS = 10000;

    private final Class<T> clazz;
    private final String type;
    private final Gson gson = new Gson();
    private final HeadersHttpClient headersClient = new HeadersJsonHttpClient();
    private final PasswordProvider passwordProvider = new PasswordProvider();

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
        CloseableHttpResponse response = headersClient.get(url, RequestUtils.createHeaderMap(passwordProvider, null));
        String json =  ResponseUtils.getEntity(response);
        return gson.fromJson(json, new ListType<>(clazz));

    }

    @Override
    public T get(String id) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        CloseableHttpResponse response = headersClient.get(url, RequestUtils.createHeaderMap(passwordProvider, null));
        String json =  ResponseUtils.getEntity(response);
        return gson.fromJson(json, clazz);
    }

    @Override
    public T create(T itemToCreate) {
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = gson.toJson(itemToCreate);
        CloseableHttpResponse response = headersClient.post(url, json, RequestUtils.createHeaderMap(passwordProvider, null));
        String resultJson =  ResponseUtils.getEntity(response);
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
        CloseableHttpResponse response = headersClient.put(url, json, RequestUtils.createHeaderMap(passwordProvider, null));
        String resultJson =  ResponseUtils.getEntity(response);
        return gson.fromJson(resultJson, clazz);
    }

    @Override
    public T delete(String id) {
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        CloseableHttpResponse response = headersClient.delete(url, RequestUtils.createHeaderMap(passwordProvider, null));
        String json =  ResponseUtils.getEntity(response);
        return gson.fromJson(json, clazz);
    }

    @Override
    public void ping() {
        RequestUtils.ping(pingTimeout);
    }
}
