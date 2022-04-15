package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    Resume read(InputStream is) throws IOException;

    void write(OutputStream os, Resume r) throws IOException;
}
