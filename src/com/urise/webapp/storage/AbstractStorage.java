package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        T searchKey = getSearchKeyIfExist(r.getUuid());
        doUpdate(r, searchKey);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        T searchKey = getSearchKeyIfNotExist(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T searchKey = getSearchKeyIfExist(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T searchKey = getSearchKeyIfExist(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = Arrays.asList(getAll());
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
    }

    protected abstract Resume[] getAll();

    protected abstract void doDelete(T searchKey);

    protected abstract Resume doGet(T searchKey);

    protected abstract void doUpdate(Resume r, T searchKey);

    protected abstract void doSave(Resume r, T searchKey);

    protected T getSearchKeyIfExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected T getSearchKeyIfNotExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(T searchKey);

    protected abstract T getSearchKey(String uuid);
}
