package com.trevorism.data.exception;

public class DeserializationException extends RuntimeException{

    public DeserializationException(String message, Throwable e){
        super(message, e);
    }
}
