package com.trevorism.data;

import com.trevorism.https.SecureHttpClient;

/**
 * @author tbrooks
 */
public class PingingDatastoreRepository<T> extends FastDatastoreRepository<T> {

    public PingingDatastoreRepository(Class<T> clazz) {
        super(clazz);
    }

    public PingingDatastoreRepository(Class<T> clazz, SecureHttpClient secureHttpClient) {
        super(clazz, secureHttpClient);
        ping();
    }

}
