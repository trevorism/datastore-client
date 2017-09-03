package com.trevorism.data.correlated;

import com.google.gson.Gson;
import com.trevorism.data.ListType;
import com.trevorism.data.PingUtils;
import com.trevorism.http.headers.HeadersHttpClient;
import com.trevorism.http.headers.HeadersJsonHttpClient;
import com.trevorism.http.util.ResponseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.trevorism.data.PingUtils.DATASTORE_BASE_URL;
/**
 * @author tbrooks
 */
public class CorrelatedDatastoreRepository<T> implements CorrelatedRepository<T> {

    private static final long DEFAULT_TIMEOUT_MILLIS = 10000;

    private final Class<T> clazz;
    private final String type;
    private final Gson gson = new Gson();
    private final HeadersHttpClient client = new HeadersJsonHttpClient();

    private final long pingTimeout;

    public CorrelatedDatastoreRepository(Class<T> clazz) {
        this(clazz, DEFAULT_TIMEOUT_MILLIS);
    }

    public CorrelatedDatastoreRepository(Class<T> clazz, long pingTimeout) {
        this.clazz = clazz;
        this.type = clazz.getSimpleName().toLowerCase();
        this.pingTimeout = pingTimeout;
    }

    @Override
    public List<T> list(String correlationId) {
        Map<String, String> headersMap = getHeadersMap(correlationId);

        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = ResponseUtils.getEntity(client.get(url, headersMap));
        return gson.fromJson(json, new ListType<>(clazz));

    }

    @Override
    public T get(String id, String correlationId) {
        Map<String, String> headersMap = getHeadersMap(correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = ResponseUtils.getEntity(client.get(url, headersMap));
        return gson.fromJson(json, clazz);
    }

    @Override
    public T create(T itemToCreate, String correlationId) {
        Map<String, String> headersMap = getHeadersMap(correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type;
        String json = gson.toJson(itemToCreate);
        String resultJson = ResponseUtils.getEntity(client.post(url, json, headersMap));
        return gson.fromJson(resultJson, clazz);
    }

    /**
     * Update will ignore nulls and changes to the id
     * @param id The id of the object being updated.
     * @param itemToUpdate The new object.
     * @return The updated object
     */
    @Override
    public T update(String id, T itemToUpdate, String correlationId) {
        Map<String, String> headersMap = getHeadersMap(correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = gson.toJson(itemToUpdate);
        String resultJson = ResponseUtils.getEntity(client.put(url, json, headersMap));
        return gson.fromJson(resultJson, clazz);
    }

    @Override
    public T delete(String id, String correlationId) {
        Map<String, String> headersMap = getHeadersMap(correlationId);
        String url = DATASTORE_BASE_URL + "/api/" + type + "/" + id;
        String json = ResponseUtils.getEntity(client.get(url, headersMap));
        return gson.fromJson(json, clazz);
    }

    @Override
    public void ping() {
        PingUtils.ping(pingTimeout);
    }

    private Map<String, String> getHeadersMap(String correlationId) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(HeadersHttpClient.CORRELATION_ID_HEADER_KEY, correlationId);
        return headersMap;
    }
}
