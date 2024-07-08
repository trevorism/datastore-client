package com.trevorism.data;

import com.trevorism.http.HttpClient;

import java.util.Arrays;
import java.util.List;

/**
 * @author tbrooks
 */
final class RequestUtils {

    static final String DATASTORE_BASE_URL = "https://datastore.data.trevorism.com";
    private static final List<Long> retryWaits = Arrays.asList(1000L, 5000L, 10000L, 15000L);

    private RequestUtils(){}

    static void ping(HttpClient client) {
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
}
