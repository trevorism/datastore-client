package com.trevorism.data.exception;

public class DataOperationException extends RuntimeException{

    public DataOperationException(String message, Throwable e){
        super(message, e);
    }
}

