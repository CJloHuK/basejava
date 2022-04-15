package com.urise.webapp.storage;

import com.urise.webapp.storage.serializestrategy.ObjectSerialize;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.toString(), new ObjectSerialize()));
    }

}