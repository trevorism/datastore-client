package com.trevorism.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trevorism.http.HeadersHttpResponse;
import com.trevorism.http.HttpClient;
import com.trevorism.https.SecureHttpClient;
import com.trevorism.https.token.ObtainTokenStrategy;

import java.util.Arrays;
import java.util.Map;

public class StubSecureHttpClient implements SecureHttpClient {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @Override
    public ObtainTokenStrategy getObtainTokenStrategy() {
        return null;
    }

    @Override
    public HttpClient getHttpClient() {
        return null;
    }

    @Override
    public String get(String s) {
        if(s.equals("https://datastore.data.trevorism.com/ping")) {
            return "pong";
        }
        if(s.equals("https://datastore.data.trevorism.com/object/testentity")){
            return gson.toJson(Arrays.asList(DatastoreRepositoryTest.createSampleEvent()));
        }
        if(s.equals("https://datastore.data.trevorism.com/object/testentity/111111")) {
            throw new RuntimeException("Not Found");
        }
        return gson.toJson(DatastoreRepositoryTest.createSampleEvent());
    }

    @Override
    public HeadersHttpResponse get(String s, Map<String, String> map) {
        return new HeadersHttpResponse(get(s), map);
    }

    @Override
    public String post(String s, String s1) {
        if(s1.equals("{\"limit\":1}")){
            return gson.toJson(Arrays.asList(DatastoreRepositoryTest.createSampleEvent()));
        }
        if(s1.equals("{\"page\":1,\"pageSize\":1}")) {
            return gson.toJson(Arrays.asList(DatastoreRepositoryTest.createSampleEvent()));
        }
        if(s1.equals("{\"sorts\":[{\"field\":\"service\",\"descending\":false}]}")){
            return gson.toJson(Arrays.asList(DatastoreRepositoryTest.createSampleEvent()));
        }
        if(s1.equals("{\"type\":\"and\",\"simpleFilters\":[{\"type\":\"string\",\"field\":\"application\",\"operator\":\">\",\"value\":\"test\"}]}"))
            return gson.toJson(Arrays.asList(DatastoreRepositoryTest.createSampleEvent()));

        return s1;
    }

    @Override
    public HeadersHttpResponse post(String s, String s1, Map<String, String> map) {
        return new HeadersHttpResponse(post(s,s1), map);
    }

    @Override
    public String put(String s, String s1) {
        if(s1.equals("{\"id\":1488418,\"application\":\"realApp\"}")){
            return "{\"id\":123456,\"application\":\"realApp\"}";
        }
        if(s1.equals("{\"id\":0,\"application\":\"realApp\"}"))
            throw new RuntimeException("Invalid ID");
        return s1;
    }

    @Override
    public HeadersHttpResponse put(String s, String s1, Map<String, String> map) {
        return new HeadersHttpResponse(put(s,s1), map);
    }

    @Override
    public String patch(String s, String s1) {
        return s1;
    }

    @Override
    public HeadersHttpResponse patch(String s, String s1, Map<String, String> map) {
        return new HeadersHttpResponse(patch(s,s1), map);
    }

    @Override
    public String delete(String s) {
        if(s.equals("https://datastore.data.trevorism.com/object/testentity/111111")) {
            throw new RuntimeException("Not Found");
        }

        return gson.toJson(DatastoreRepositoryTest.createSampleEvent());
    }

    @Override
    public HeadersHttpResponse delete(String s, Map<String, String> map) {
        return new HeadersHttpResponse(delete(s), map);
    }
}
