package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

public class DataSerialize implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeCollection(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((TextListSection) section).getDescriptionList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((WorkSection) section).getWorkList(), (work -> {
                            dos.writeUTF(work.getTitle());
                            writeCollection(dos, work.getDescriptionList(), workDescription -> {
                                dos.writeUTF(workDescription.getStartDate().toString());
                                dos.writeUTF(workDescription.getEndDate().toString());
                                dos.writeUTF(workDescription.getTitle());
                                dos.writeUTF(workDescription.getDescription());
                            });
                        }));
                        break;
                }
            });
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                TextListSection section = new TextListSection();
                readItems(dis, () -> section.addDescription(dis.readUTF()));
                return section;
            case EXPERIENCE:
            case EDUCATION:
                WorkSection workSection = new WorkSection();
                readItems(dis, () -> {
                    Work work = new Work(dis.readUTF());
                    readItems(dis, () -> {
                        work.addDescription(new Work.WorkDescription(
                                LocalDate.parse(dis.readUTF()),
                                LocalDate.parse(dis.readUTF()),
                                dis.readUTF(),
                                dis.readUTF()));
                    });
                    workSection.addWork(work);
                });
                return workSection;
            default:
                return null;
        }
    }

}
