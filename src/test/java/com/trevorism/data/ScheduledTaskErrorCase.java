package com.trevorism.data;

import com.trevorism.data.deserialize.ComplexObject;
import org.junit.Test;

/**
 * @author tbrooks
 */
public class ScheduledTaskErrorCase {

    Repository<ComplexObject> repository = new DatastoreRepository<>(ComplexObject.class);

    @Test
    public void testOps(){


        ComplexObject asdf = repository.create(ComplexObject.createSample());

        System.out.println(asdf);

    }

}
