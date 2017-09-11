package com.trevorism.data;

import com.trevorism.http.HttpClient;
import com.trevorism.http.JsonHttpClient;
import com.trevorism.http.headers.HeadersHttpClient;
import com.trevorism.secure.PasswordProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tbrooks
 */
public class RequestUtils {

    public static final String DATASTORE_BASE_URL = "http://datastore.trevorism.com";
    private static final HttpClient client = new JsonHttpClient();


    public static void ping(long timeout) {
        try {
            //ping the API to wake it up since it is not always on
            String pong = client.get(DATASTORE_BASE_URL + "/ping");
            if (!"pong".equals(pong))
                throw new Exception("Unable to ping events");
        } catch (Exception e) {
            try {
                Thread.sleep(timeout);
                String pong = client.get(DATASTORE_BASE_URL + "/ping");
                if (!"pong".equals(pong))
                    throw new RuntimeException("Unable to ping events after 10 second retry");
            } catch (InterruptedException ie) {
                throw new RuntimeException("Interrupted failure", ie);
            }
        }
    }

    public static Map<String, String> createHeaderMap(PasswordProvider passwordProvider, String correlationId) {
        Map<String, String> headersMap = new HashMap<>();
        if(correlationId != null)
            headersMap.put(HeadersHttpClient.CORRELATION_ID_HEADER_KEY, correlationId);
        headersMap.put(PasswordProvider.AUTHORIZATION_HEADER, passwordProvider.getPassword());
        return headersMap;
    }

}
