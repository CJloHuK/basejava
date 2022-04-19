package com.urise.webapp.storage.serializestrategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataSerialize implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
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

    @Override
    public Resume doRead(InputStream is) throws IOException {
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
