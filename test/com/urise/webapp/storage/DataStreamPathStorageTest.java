package com.urise.webapp.storage;

import com.urise.webapp.storage.serializestrategy.DataSerialize;

public class DataStreamPathStorageTest extends AbstractStorageTest{

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataSerialize()));
    }

}