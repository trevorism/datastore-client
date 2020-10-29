package com.trevorism.data;

import com.trevorism.https.token.ObtainTokenFromAuthService;
import com.trevorism.https.token.ObtainTokenStrategy;

import java.util.List;

/**
 * @author tbrooks
 */
public class PingingDatastoreRepository<T> implements Repository<T> {

    private final FastDatastoreRepository<T> delegate;

    public PingingDatastoreRepository(Class<T> clazz) {
        this(clazz, null);
    }

    public PingingDatastoreRepository(Class<T> clazz, ObtainTokenStrategy obtainTokenStrategy) {
        delegate = new FastDatastoreRepository<T>(clazz, obtainTokenStrategy);
    }

    @Override
    public List<T> list() {
        ping();
        return delegate.list();
    }

    @Override
    public List<T> list(String correlationId) {
        ping();
        return delegate.list(correlationId);
    }

    @Override
    public T get(String id) {
        ping();
        return delegate.get(id);
    }

    @Override
    public T get(String id, String correlationId) {
        ping();
        return delegate.get(id, correlationId);
    }

    @Override
    public T create(T itemToCreate) {
        ping();
        return delegate.create(itemToCreate);
    }

    @Override
    public T create(T itemToCreate, String correlationId) {
        ping();
        return delegate.create(itemToCreate, correlationId);
    }

    @Override
    public T update(String id, T itemToUpdate) {
        ping();
        return delegate.update(id, itemToUpdate);
    }

    @Override
    public T update(String id, T itemToUpdate, String correlationId) {
        ping();
        return delegate.update(id, itemToUpdate, correlationId);
    }

    @Override
    public T delete(String id) {
        ping();
        return delegate.delete(id);
    }

    @Override
    public T delete(String id, String correlationId) {
        ping();
        return delegate.delete(id, correlationId);
    }

    @Override
    public void ping() {
        delegate.ping();
    }
}
