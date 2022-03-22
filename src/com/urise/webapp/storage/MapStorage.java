package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected Object getSearchKeyIfExist(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    protected Object getSearchKeyIfNotExist(Resume r) {
        String uuid = r.getUuid();
        if (!storage.containsKey(uuid)) {
            return uuid;
        }
        throw new ExistStorageException(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
