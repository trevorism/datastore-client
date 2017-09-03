package com.trevorism.data;

import com.trevorism.http.HttpClient;
import com.trevorism.http.JsonHttpClient;

/**
 * @author tbrooks
 */
public class PingUtils {

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
}
