package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerialize implements SerializeStrategy{
    XmlParser xmlParser;

    public XmlSerialize() {
        xmlParser = new XmlParser(Resume.class, TextListSection.class, TextSection.class, Work.class, Work.WorkDescription.class, WorkSection.class);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return xmlParser.unmarshall(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        xmlParser.marshall(r, new OutputStreamWriter(os, StandardCharsets.UTF_8));
    }
}
