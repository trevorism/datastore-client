package com.trevorism.data;

import com.trevorism.http.HttpClient;
import com.trevorism.http.JsonHttpClient;
import com.trevorism.http.headers.HeadersHttpClient;
import com.trevorism.secure.PasswordProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tbrooks
 */
final class RequestUtils {

    static final String DATASTORE_BASE_URL = "https://datastore.trevorism.com";
    private static final HttpClient client = new JsonHttpClient();
    private static final List<Long> retryWaits = Arrays.asList(1000L, 5000L, 10000L, 15000L);

    private RequestUtils(){}

    static void ping() {
        for(Long retryWait : retryWaits){
            try {
                //ping the API to wake it up since it is not always on
                String pong = client.get(DATASTORE_BASE_URL + "/ping");
                if (!"pong".equals(pong))
                    throw new Exception("Unable to ping datastore");
            } catch (Exception e) {
                try {
                    Thread.sleep(retryWait);
                } catch (InterruptedException ie) {
                    throw new RuntimeException("Interrupted failure", ie);
                }
            }
        }
    }

    static Map<String, String> createHeaderMap(String correlationId) {
        Map<String, String> headersMap = new HashMap<>();
        if(correlationId != null)
            headersMap.put(HeadersHttpClient.CORRELATION_ID_HEADER_KEY, correlationId);
        headersMap.put("Authorization", PasswordProvider.getInstance().getPassword());
        return headersMap;
    }

}
