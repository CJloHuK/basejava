package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                                String description = workDescription.getDescription();
                                dos.writeUTF(description == null ? "null" : description);
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
                        LocalDate startDate = LocalDate.parse(dis.readUTF());
                        LocalDate endDate = LocalDate.parse(dis.readUTF());
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        work.addDescription(new Work.WorkDescription(startDate, endDate, title, description.equals("null") ? null : description));
                    });
                    workSection.addWork(work);
                });
                return workSection;
            default:
                return null;
        }
    }


    public void doWriteOld(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section value = entry.getValue();
                if (value instanceof TextSection) {
                    dos.writeUTF("TextSection");
                    dos.writeUTF(((TextSection) value).getDescription());
                } else if (value instanceof TextListSection) {
                    dos.writeUTF("TextListSection");
                    List<String> descriptionList = ((TextListSection) value).getDescriptionList();
                    dos.writeInt(descriptionList.size());
                    for (String str : descriptionList) {
                        dos.writeUTF(str);
                    }
                } else if (value instanceof WorkSection) {
                    dos.writeUTF("WorkSection");
                    List<Work> workList = ((WorkSection) value).getWorkList();
                    dos.writeInt(workList.size());
                    for (Work work : workList) {
                        dos.writeUTF(work.getTitle());
                        List<Work.WorkDescription> descriptionList = work.getDescriptionList();
                        dos.writeInt(descriptionList.size());
                        for (Work.WorkDescription workDescription : descriptionList) {
                            dos.writeUTF(workDescription.getStartDate().toString());
                            dos.writeUTF(workDescription.getEndDate().toString());
                            dos.writeUTF(workDescription.getTitle());
                            String description = workDescription.getDescription();
                            dos.writeUTF(description == null ? "null" : description);
                        }
                    }
                }
            }
        }
    }


    public Resume doReadOld(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String key = dis.readUTF();
                String type = dis.readUTF();
                if ("TextSection".equals(type)) {
                    resume.addSection(SectionType.valueOf(key), new TextSection(dis.readUTF()));
                } else if ("TextListSection".equals(type)) {
                    TextListSection textListSection = new TextListSection();
                    int sizeDesc = dis.readInt();
                    for (int j = 0; j < sizeDesc; j++) {
                        textListSection.addDescription(dis.readUTF());
                    }
                    resume.addSection(SectionType.valueOf(key), textListSection);
                } else if ("WorkSection".equals(type)) {
                    WorkSection workSection = new WorkSection();
                    int sizeWork = dis.readInt();
                    for (int j = 0; j < sizeWork; j++) {
                        Work work = new Work(dis.readUTF());
                        int sizeWorkDesc = dis.readInt();
                        for (int k = 0; k < sizeWorkDesc; k++) {
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            work.addDescription(new Work.WorkDescription(startDate, endDate, title, description.equals("null") ? null : description));
                        }
                        workSection.addWork(work);
                    }
                    resume.addSection(SectionType.valueOf(key), workSection);
                }
            }
            return resume;
        }
    }

}
