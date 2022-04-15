package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume r, OutputStream os) throws IOException;
}
