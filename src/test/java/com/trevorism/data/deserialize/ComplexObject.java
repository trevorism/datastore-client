package com.trevorism.data.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * @author tbrooks
 */
public class ComplexObject {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    private String id;
    private Date date;
    private List<Boolean> yesNo;
    private List<String> items;
    private Map<String, Object> object;
    private Map<String,String> keyValue;
    private List<SimpleObject> objects;


    public static ComplexObject createSample(){
        ComplexObject task = new ComplexObject();

        task.id = "112233";
        task.date = new Date();
        task.yesNo = Arrays.asList(true,false,true);
        task.items = Arrays.asList("hi","hello","yo");
        task.object = new HashMap<>();
        task.object.put("complexDate", new Date());
        task.object.put("complexString", "wow");

        task.keyValue = new HashMap<>();
        task.keyValue.put("key","value");

        task.objects = Arrays.asList(SimpleObject.createSample1(), SimpleObject.createSample2());

        return task;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Boolean> getYesNo() {
        return yesNo;
    }

    public void setYesNo(List<Boolean> yesNo) {
        this.yesNo = yesNo;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public Map<String, Object> getObject() {
        return object;
    }

    public void setObject(Map<String, Object> object) {
        this.object = object;
    }

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    public List<SimpleObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SimpleObject> objects) {
        this.objects = objects;
    }

}
