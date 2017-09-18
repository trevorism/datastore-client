package com.trevorism.data.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author tbrooks
 */
public class DatastoreDeserializerTest {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @Test
    public void deserializeComplex() throws Exception {
        ComplexObject complexObject = ComplexObject.createSample();
        String json = gson.toJson(complexObject);

        System.out.println(json);

        DatastoreDeserializer<ComplexObject> deserializer = new DatastoreDeserializer<>();
        ComplexObject deserialized = deserializer.deserializeJsonObject(json, ComplexObject.class);

        System.out.println(deserialized);
        Assert.assertEquals(json, deserialized.toString());

    }

    @Test
    public void deserializeSimple1() throws Exception {
        SimpleObject simpleObject = SimpleObject.createSample1();
        String json = gson.toJson(simpleObject);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        SimpleObject deserialized = deserializer.deserializeJsonObject(json, SimpleObject.class);

        Assert.assertEquals(json, deserialized.toString());
    }

    @Test
    public void deserializeSimpleList() throws Exception {
        List<SimpleObject> list = Arrays.asList(SimpleObject.createSample1(), SimpleObject.createSample2());
        String json = gson.toJson(list);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        List<SimpleObject> simpleObjects = deserializer.deserializeJsonArray(json, SimpleObject.class);

        Assert.assertEquals(gson.toJson(SimpleObject.createSample1()), simpleObjects.get(0).toString());
        Assert.assertEquals(gson.toJson(SimpleObject.createSample2()), simpleObjects.get(1).toString());

    }

}