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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

public class DatastoreRepositoryTest {

    Repository<TestEntity> repository;

    @BeforeEach
    public void setup() {
        repository = new FastDatastoreRepository<>(TestEntity.class, new StubSecureHttpClient());
        repository.ping();
        TestEntity event = createSampleEvent();
        TestEntity createdEvent = repository.create(event);
        assertEquals(event.getId(), createdEvent.getId());
        assertEquals(event.getVersion(), createdEvent.getVersion());
        assertEquals(event.getService(), createdEvent.getService());
        assertEquals(event.getApplication(), createdEvent.getApplication());
    }

    @AfterEach
    public void tearDown() {
        repository.delete("123456");
    }

    @Test
    public void list() throws InterruptedException {
        List<TestEntity> list = repository.list();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void get() {
        TestEntity event = repository.get("123456");
        assertEquals(123456L, event.getId());
        assertNull(event.getVersion());
        assertEquals("test", event.getService());
        assertEquals("testApp", event.getApplication());
    }

    @Test
    public void update() {
        TestEntity event = new TestEntity();
        event.setApplication("realApp");
        event.setId(1488418);
        TestEntity updatedEntity = repository.update("123456", event);
        assertEquals(123456L, updatedEntity.getId());
        assertEquals("realApp", updatedEntity.getApplication());
        assertNull(updatedEntity.getService());

    }

    @Test
    public void getMissingId() {
        assertThrows(DataOperationException.class, () -> repository.get("111111"));
    }

    @Test
    public void updateMissingId() {
        TestEntity event = new TestEntity();
        event.setApplication("realApp");

        assertThrows(DataOperationException.class, () -> repository.update("111111", event));
    }

    @Test
    public void deleteMissingId() {
        assertThrows(DataOperationException.class, () ->  repository.delete("111111"));
    }

    @Test
    public void testFilter() {
        ComplexFilter complexFilter = new FilterBuilder().addFilter(
                new SimpleFilter("application", FilterConstants.OPERATOR_GREATER_THAN, "test")
        ).build();

        List<TestEntity> list = repository.filter(complexFilter);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testPage() {
        List<TestEntity> list = repository.page(new Page(1, 1));
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testLimit() {
        List<TestEntity> list = repository.page(new Limit(1));
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testSort() {
        ComplexSort complexSort = new SortBuilder().addSort(new Sort("service", false)).build();
        List<TestEntity> list = repository.sort(complexSort);
        assertNotNull(list);
        assertEquals(1, list.size());
    }


    public static TestEntity createSampleEvent() {
        TestEntity event = new TestEntity();
        event.setDate(new Date());
        event.setId(123456L);
        event.setService("test");
        event.setApplication("testApp");
        return event;
    }

}