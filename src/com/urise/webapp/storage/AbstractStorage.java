package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object searchKey = getSearchKeyIfExist(r.getUuid());
        doUpdate(r, searchKey);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getSearchKeyIfNotExist(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKeyIfExist(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKeyIfExist(uuid);
        return doGet(searchKey);
    }

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);

    protected Object getSearchKeyIfExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if(!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Object getSearchKeyIfNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if(isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);
}
