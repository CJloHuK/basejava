package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializestrategy.SerializeStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage{
    private SerializeStrategy strategy;

    public ObjectStreamPathStorage(String dir, SerializeStrategy strategy) {
        super(dir);
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
