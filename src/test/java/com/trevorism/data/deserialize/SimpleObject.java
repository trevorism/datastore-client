package com.trevorism.data.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * @author tbrooks
 */
public class SimpleObject {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    private long id;
    private String name;
    private boolean trueFalse;
    private float num;


    public static SimpleObject createSample1(){
        SimpleObject sample1 = new SimpleObject();
        sample1.id = 12345;
        sample1.name = "sample1";
        sample1.trueFalse = true;
        sample1.num = 4.2f;
        return sample1;
    }

    public static SimpleObject createSample2(){
        SimpleObject sample2 = new SimpleObject();
        sample2.id = 54321;
        sample2.name = "sample2";
        sample2.trueFalse = true;
        sample2.num = -2.1f;
        return sample2;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

}
