package com.urise.webapp.storage;

import com.urise.webapp.storage.serializestrategy.JsonSerialize;

public class JsonStreamPathStorageTest extends AbstractStorageTest{

    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new JsonSerialize()));
    }

}