package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPasswrod) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPasswrod));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    deleteTable(conn, r, "contact");
                    deleteTable(conn, r, "section");
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
//                StringBuilder res = new StringBuilder();
//                Section section = e.getValue();
//                if (section instanceof TextSection) {
//                    res = new StringBuilder(((TextSection) section).getDescription());
//                } else if (section instanceof TextListSection) {
//                    for (String str : ((TextListSection) section).getDescriptionList()) {
//                        if (res.length() > 0) {
//                            res.append(System.lineSeparator());
//                        }
//                        res.append(str);
//                    }
//                }
//                ps.setString(3, res.toString());
                ps.setString(3, JsonParser.write(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteTable(Connection conn, Resume r, String table) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + table + " WHERE resume_uuid =?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    @Override
    public Resume get(String uuid) {
//        return sqlHelper.execute("" +
//                        "    SELECT r.uuid, r.full_name, c.type, c.value, s.type as sType, s.value as sValue  FROM resume r " +
//                        " LEFT JOIN contact c " +
//                        "        ON r.uuid = c.resume_uuid " +
//                        " LEFT JOIN section s " +
//                        "        ON r.uuid = s.resume_uuid " +
//                        "     WHERE r.uuid =? ",
//                ps -> {
//                    ps.setString(1, uuid);
//                    ResultSet rs = ps.executeQuery();
//                    if (!rs.next()) {
//                        throw new NotExistStorageException(uuid);
//                    }
//                    Resume r = new Resume(uuid, rs.getString("full_name"));
//                    do {
//                        addContactToResume(rs, r);
//                        addSectionToResume(rs, r);
//                    } while (rs.next());
//
//                    return r;
//                });
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from resume where uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact where resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContactToResume(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from section where resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSectionToResume(rs, r);
                }
            }
            return r;
        });
    }

    private void addContactToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    private void addSectionToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("Value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("Type"));
//            Section section = null;
//            switch (type) {
//                case OBJECTIVE:
//                case PERSONAL:
//                    section = new TextSection(value);
//                    break;
//                case ACHIEVEMENT:
//                case QUALIFICATIONS:
//                    TextListSection textListSection = new TextListSection();
//                    String[] strings = value.split(System.lineSeparator());
//                    for (String str : strings) {
//                        textListSection.addDescription(str);
//                    }
//                    section = textListSection;
//                    break;
//                case EXPERIENCE:
//                case EDUCATION:
//                    return;
//            }
//            r.addSection(type, section);
            r.addSection(type, JsonParser.read(value, Section.class));
        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
//        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c on r.uuid = c.resume_uuid ORDER BY full_name,uuid", ps -> {
//            ResultSet rs = ps.executeQuery();
//            List<Resume> list = new ArrayList<>();
//            String lastUuid = null;
//            Resume r = null;
//            while (rs.next()) {
//                String uuid = rs.getString("uuid").trim();
//                if(!uuid.equals(lastUuid)) {
//                    r = new Resume(uuid, rs.getString("full_name"));
//                    list.add(r);
//                }
//                lastUuid = uuid;
//                addContactToResume(rs, r);
//            }
//            return list;
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid").trim();
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid").trim());
                    addContactToResume(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid").trim());
                    addSectionToResume(rs, r);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
