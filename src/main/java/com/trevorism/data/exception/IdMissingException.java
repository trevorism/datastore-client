package com.trevorism.data.exception;

/**
 * @author tbrooks
 */
public class IdMissingException extends RuntimeException {
    public IdMissingException(String id, String correlationId){
        super("Unable to find id: " + id + " in the datastore. Correlation ID: " + correlationId);
    }
}
