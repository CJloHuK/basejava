package com.urise.webapp.storage;

import com.urise.webapp.storage.serializestrategy.XmlSerialize;

public class XmlStreamPathStorageTest extends AbstractStorageTest{

    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlSerialize()));
    }

}