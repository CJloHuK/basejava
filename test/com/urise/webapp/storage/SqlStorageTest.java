package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.StorageException;
import org.junit.Test;

public class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }

    @Override
    @Test(expected = StorageException.class)
    public void saveAlreadyExist() throws Exception {
        super.saveAlreadyExist();
    }

    @Override
    @Test
    public void deleteNotExist() throws Exception {
    }

    @Override
    @Test
    public void updateNotExist() throws Exception {
    }
}