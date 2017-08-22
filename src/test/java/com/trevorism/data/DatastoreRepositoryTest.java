package com.trevorism.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author tbrooks
 */
public class DatastoreRepositoryTest {

    Repository<TestEntity> repository = new DatastoreRepository<>(TestEntity.class);

    @Before
    public void setup(){
        repository.ping();
        TestEntity event = createSampleEvent();
        TestEntity createdEvent = repository.create(event);
        Assert.assertEquals(event.getId(), createdEvent.getId());
        Assert.assertEquals(event.getVersion(), createdEvent.getVersion());
        Assert.assertEquals(event.getService(), createdEvent.getService());
        Assert.assertEquals(event.getApplication(), createdEvent.getApplication());
    }

    @After
    public void tearDown(){
        repository.delete("123456");
    }

    @Test
    public void list() throws InterruptedException {
        Thread.sleep(1000);
        List<TestEntity> list = repository.list();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void get()  {
        TestEntity event = repository.get("123456");
        Assert.assertEquals(123456L, event.getId());
        Assert.assertNull(event.getVersion());
        Assert.assertEquals("test", event.getService());
        Assert.assertEquals("testApp", event.getApplication());
    }

    @Test
    public void update() {
        TestEntity event = new TestEntity();
        event.setApplication("realApp");
        event.setId(1488418);
        TestEntity updatedEntity = repository.update("123456", event);
        Assert.assertEquals(123456L, updatedEntity.getId());
        Assert.assertEquals("realApp", updatedEntity.getApplication());
        Assert.assertEquals("test", updatedEntity.getService());

    }


    private TestEntity createSampleEvent() {
        TestEntity event = new TestEntity();
        event.setDate(new Date());
        event.setId(123456L);
        event.setService("test");
        event.setApplication("testApp");
        return event;
    }

}