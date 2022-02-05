package com.trevorism.data.model.filtering;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SimpleFilter {
    private final String type;
    private final String field;
    private final String operator;
    private final String value;

    public SimpleFilter(String field, String operator, String value){
        this.type = FilterConstants.TYPE_STRING;
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public SimpleFilter(String field, String operator, boolean bool){
        this.type = FilterConstants.TYPE_BOOLEAN;
        this.field = field;
        this.operator = operator;
        this.value = String.valueOf(bool);
    }

    public SimpleFilter(String field, String operator, Date date){
        this.type = FilterConstants.TYPE_DATE;
        this.field = field;
        this.operator = operator;
        this.value = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);
    }

    public SimpleFilter(String field, String operator, Number number){
        this.type = FilterConstants.TYPE_NUMBER;
        this.field = field;
        this.operator = operator;
        this.value = String.valueOf(number);
    }

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
