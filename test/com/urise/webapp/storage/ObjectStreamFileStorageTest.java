package com.urise.webapp.storage;

import com.urise.webapp.storage.serializestrategy.ObjectSerialize;

public class ObjectStreamFileStorageTest extends AbstractStorageTest{

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerialize()));
    }

}