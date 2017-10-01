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
    public void deserializeEmptyArray(){
        String json = "[{}]";
        DatastoreDeserializer<ComplexObject> deserializer = new DatastoreDeserializer<>();
        List<ComplexObject> deserializedList = deserializer.deserializeJsonArray(json, ComplexObject.class);

        Assert.assertEquals(1, deserializedList.size());
        Assert.assertNotNull(deserializedList.get(0));
        Assert.assertNull(deserializedList.get(0).getId());
        Assert.assertNull(deserializedList.get(0).getDate());
        Assert.assertNull(deserializedList.get(0).getObjects());

    }

    @Test
    public void deserializeComplex(){
        ComplexObject complexObject = ComplexObject.createSample();
        String json = gson.toJson(complexObject);

        DatastoreDeserializer<ComplexObject> deserializer = new DatastoreDeserializer<>();
        ComplexObject deserialized = deserializer.deserializeJsonObject(json, ComplexObject.class);

        Assert.assertEquals(json, deserialized.toString());

    }

    @Test
    public void deserializeSimple1() {
        SimpleObject simpleObject = SimpleObject.createSample1();
        String json = gson.toJson(simpleObject);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        SimpleObject deserialized = deserializer.deserializeJsonObject(json, SimpleObject.class);

        Assert.assertEquals(json, deserialized.toString());
    }

    @Test
    public void deserializeSimpleList() {
        List<SimpleObject> list = Arrays.asList(SimpleObject.createSample1(), SimpleObject.createSample2());
        String json = gson.toJson(list);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        List<SimpleObject> simpleObjects = deserializer.deserializeJsonArray(json, SimpleObject.class);

        Assert.assertEquals(gson.toJson(SimpleObject.createSample1()), simpleObjects.get(0).toString());
        Assert.assertEquals(gson.toJson(SimpleObject.createSample2()), simpleObjects.get(1).toString());

    }

    @Test
    public void deserializeStringIntoMap(){
        String json = "{\"keyValue\":\"{\\\"key\\\": \\\"value\\\"}\"}";

        DatastoreDeserializer<MapAndListAsString> deserializer = new DatastoreDeserializer<>();
        MapAndListAsString obj = deserializer.deserializeJsonObject(json, MapAndListAsString.class);

        Assert.assertEquals("value", obj.getKeyValue().get("key"));

    }

    @Test
    public void deserializeStringIntoList(){
        String json = "{\"items\":\"[\\\"item1\\\", \\\"item2\\\"]\"}";
        DatastoreDeserializer<MapAndListAsString> deserializer = new DatastoreDeserializer<>();
        MapAndListAsString obj = deserializer.deserializeJsonObject(json, MapAndListAsString.class);

        Assert.assertEquals(2, obj.getItems().size());
        Assert.assertEquals("item1", obj.getItems().get(0));
        Assert.assertEquals("item2", obj.getItems().get(1));

    }
}