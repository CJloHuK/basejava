package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializestrategy.SerializeStrategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage {
    private SerializeStrategy strategy;

    public ObjectStreamStorage(File directory, SerializeStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return strategy.read(is);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        strategy.write(os, r);
    }
}
