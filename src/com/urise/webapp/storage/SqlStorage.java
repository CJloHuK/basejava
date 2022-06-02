package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPasswrod) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPasswrod);
    }

    @Override
    public void clear() {
        SqlHelper.execute(connectionFactory, "DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        SqlHelper.execute(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        SqlHelper.execute(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.execute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.execute(connectionFactory, "DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.execute(connectionFactory, "SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            Comparator<Resume> comparator = (o1, o2) -> {
                int comparing = o1.getFullName().compareTo(o2.getFullName());
                if (comparing == 0) {
                    return o1.getUuid().compareTo(o2.getUuid());
                } else {
                    return comparing;
                }
            };
            list.sort(comparator);
            return list;
        });
    }

    @Override
    public int size() {
        return SqlHelper.execute(connectionFactory, "SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            int size = 0;
            while (rs.next()) {
                size++;
            }
            return size;
        });
    }
}
