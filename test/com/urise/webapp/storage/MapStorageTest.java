package com.urise.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest{

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Test
    @Override
    public void overflowTest() throws Exception {
        super.overflowTest();
    }
}