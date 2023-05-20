package com.trevorism.data.exception;

/**
 * @author tbrooks
 */
public class CreationFailedException extends DataOperationException {
    public CreationFailedException(Throwable e) {
        super("Unable to create object. The most likely cause is an object with a duplicate or non-numeric id", e);
    }
}
