package com.trevorism.data.deserialize;

import com.google.gson.*;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author tbrooks
 */
public class DatastoreDeserializer<T> implements Deserializer<T>{

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();


    public T deserializeJsonObject(String jsonObject, Class<T> clazz){
        JsonParser parser = new JsonParser();
        return deserialize(parser.parse(jsonObject).getAsJsonObject(), clazz);
    }

    public List<T> deserializeJsonArray(String jsonArray, Class<T> clazz){
        JsonParser parser = new JsonParser();
        return deserialize(parser.parse(jsonArray).getAsJsonArray(), clazz);
    }

    private List<T> deserialize(JsonArray json, Class<T> clazz){
        List<T> list = new ArrayList<>();
        for (JsonElement aJson : json) {
            JsonObject jo = aJson.getAsJsonObject();
            list.add(deserialize(jo, clazz));
        }
        return list;
    }

    private T deserialize(JsonObject json, Class<T> clazz){
        try {
            T t = clazz.newInstance();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                PropertyDescriptor descriptor = findPropertyDescriptor(t, entry);
                if (descriptor != null) {
                    Object value = getValue(entry.getValue(), descriptor);
                    PropertyUtils.setProperty(t, descriptor.getName(), value);
                }
            }
            return t;
        }catch (Exception e){
            throw new RuntimeException("Unable to deserialize " + json + " into " + clazz, e);
        }
    }

    private PropertyDescriptor findPropertyDescriptor(T t, Map.Entry<String, JsonElement> entry) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String key = entry.getKey();
        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(t, key);
        if(pd == null){
            for (PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(t)) {
                if(key.toLowerCase().equals(descriptor.getName().toLowerCase())){
                    return descriptor;
                }
            }
        }
        return pd;

    }

    private Object getValue(JsonElement value, PropertyDescriptor descriptor) throws Exception {
        Class<?> type = descriptor.getPropertyType();
        if (type.equals(int.class) || type.isAssignableFrom(Integer.class)) {
            return value.getAsInt();
        } else if (type.equals(long.class) || type.isAssignableFrom(Long.class)) {
            return value.getAsLong();
        } else if (type.equals(float.class) || type.isAssignableFrom(Float.class)) {
            return value.getAsFloat();
        } else if (type.equals(double.class) || type.isAssignableFrom(Double.class)) {
            return value.getAsDouble();
        } else if (type.equals(boolean.class) || type.isAssignableFrom(Boolean.class)) {
            return value.getAsBoolean();
        } else if (type.equals(char.class) || type.isAssignableFrom(Character.class)) {
            return value.getAsCharacter();
        } else if (type.isAssignableFrom(Date.class)) {
            return gson.fromJson(value, Date.class);
        } else if (type.isAssignableFrom(List.class)) {
            return handleListType(value, descriptor);
        } else if (type.isAssignableFrom(Map.class)) {
            return gson.fromJson(value, Map.class);
        }

        return value.getAsString();
    }

    private Object handleListType(JsonElement value, PropertyDescriptor descriptor) {
        final Type gt = descriptor.getReadMethod().getGenericReturnType();
        if (gt instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) gt).getActualTypeArguments()[0];
            ListType listType = new ListType<>((Class)actualType);
            return gson.fromJson(value, listType);
        }

        return gson.fromJson(value, List.class);
    }
}
