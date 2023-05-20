package com.trevorism.data;

import com.trevorism.data.exception.DataOperationException;
import com.trevorism.data.model.filtering.ComplexFilter;
import com.trevorism.data.model.filtering.FilterBuilder;
import com.trevorism.data.model.filtering.FilterConstants;
import com.trevorism.data.model.filtering.SimpleFilter;
import com.trevorism.data.model.paging.Limit;
import com.trevorism.data.model.paging.Page;
import com.trevorism.data.model.sorting.ComplexSort;
import com.trevorism.data.model.sorting.Sort;
import com.trevorism.data.model.sorting.SortBuilder;
import com.trevorism.http.util.InvalidRequestException;
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

    Repository<TestEntity> repository = new FastDatastoreRepository<>(TestEntity.class);

    @Before
    public void setup() {
        repository.ping();
        TestEntity event = createSampleEvent();
        TestEntity createdEvent = repository.create(event);
        Assert.assertEquals(event.getId(), createdEvent.getId());
        Assert.assertEquals(event.getVersion(), createdEvent.getVersion());
        Assert.assertEquals(event.getService(), createdEvent.getService());
        Assert.assertEquals(event.getApplication(), createdEvent.getApplication());
    }

    @After
    public void tearDown() {
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
    public void get() {
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
        Assert.assertNull(updatedEntity.getService());

    }

    @Test(expected = DataOperationException.class)
    public void getMissingId() {
        TestEntity event = repository.get("111111");
        Assert.assertNull(event);
    }

    @Test(expected = DataOperationException.class)
    public void updateMissingId() {
        TestEntity event = new TestEntity();
        event.setApplication("realApp");

        TestEntity event2 = repository.update("111111", event);
        Assert.assertNull(event2);
    }

    @Test(expected = DataOperationException.class)
    public void deleteMissingId() {
        TestEntity event = repository.delete("111111");
        Assert.assertNull(event);
    }

    @Test
    public void testFilter() {
        ComplexFilter complexFilter = new FilterBuilder().addFilter(
                new SimpleFilter("application", FilterConstants.OPERATOR_GREATER_THAN, "test")
        ).build();

        List<TestEntity> list = repository.filter(complexFilter);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testPage() {
        List<TestEntity> list = repository.page(new Page(1, 1));
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testLimit() {
        List<TestEntity> list = repository.page(new Limit(1));
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testSort() {
        ComplexSort complexSort = new SortBuilder().addSort(new Sort("service", false)).build();
        List<TestEntity> list = repository.sort(complexSort);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
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