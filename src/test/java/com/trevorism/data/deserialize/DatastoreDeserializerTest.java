package com.trevorism.data.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;



/**
 * @author tbrooks
 */
public class DatastoreDeserializerTest {

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @Test
    public void deserializeEmptyArray(){
        String json = "[{}]";
        DatastoreDeserializer<ComplexObject> deserializer = new DatastoreDeserializer<>();
        List<ComplexObject> deserializedList = deserializer.deserializeJsonArray(json, ComplexObject.class);

        assertEquals(1, deserializedList.size());
        assertNotNull(deserializedList.get(0));
        assertNull(deserializedList.get(0).getId());
        assertNull(deserializedList.get(0).getDate());
        assertNull(deserializedList.get(0).getObjects());
    }

    @Test
    public void deserializeComplex(){
        ComplexObject complexObject = ComplexObject.createSample();
        String json = gson.toJson(complexObject);

        DatastoreDeserializer<ComplexObject> deserializer = new DatastoreDeserializer<>();
        ComplexObject deserialized = deserializer.deserializeJsonObject(json, ComplexObject.class);

        assertEquals(json, deserialized.toString());
    }

    @Test
    public void deserializeSimple1() {
        SimpleObject simpleObject = SimpleObject.createSample1();
        String json = gson.toJson(simpleObject);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        SimpleObject deserialized = deserializer.deserializeJsonObject(json, SimpleObject.class);

        assertEquals(json, deserialized.toString());
    }

    @Test
    public void deserializeSimpleList() {
        List<SimpleObject> list = Arrays.asList(SimpleObject.createSample1(), SimpleObject.createSample2());
        String json = gson.toJson(list);

        DatastoreDeserializer<SimpleObject> deserializer = new DatastoreDeserializer<>();
        List<SimpleObject> simpleObjects = deserializer.deserializeJsonArray(json, SimpleObject.class);

        assertEquals(gson.toJson(SimpleObject.createSample1()), simpleObjects.get(0).toString());
        assertEquals(gson.toJson(SimpleObject.createSample2()), simpleObjects.get(1).toString());
    }

    @Test
    public void deserializeStringIntoMap(){
        String json = "{\"keyValue\":\"{\\\"key\\\": \\\"value\\\"}\"}";

        DatastoreDeserializer<MapAndListAsString> deserializer = new DatastoreDeserializer<>();
        MapAndListAsString obj = deserializer.deserializeJsonObject(json, MapAndListAsString.class);

        assertEquals("value", obj.getKeyValue().get("key"));
    }

    @Test
    public void deserializeStringIntoList(){
        String json = "{\"items\":\"[\\\"item1\\\", \\\"item2\\\"]\"}";
        DatastoreDeserializer<MapAndListAsString> deserializer = new DatastoreDeserializer<>();
        MapAndListAsString obj = deserializer.deserializeJsonObject(json, MapAndListAsString.class);

        assertEquals(2, obj.getItems().size());
        assertEquals("item1", obj.getItems().get(0));
        assertEquals("item2", obj.getItems().get(1));
    }
}