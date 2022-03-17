package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        int index = storage.indexOf(r);
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage.set(index, r);
    }

    @Override
    public void save(Resume r) {
        if (storage.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        }
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        for(Resume r : storage) {
            if(r.getUuid().equals(uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(get(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
