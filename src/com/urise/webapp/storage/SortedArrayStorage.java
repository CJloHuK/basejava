package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void fillDeletedElement(int index) {
        if (size - 1 - index > 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        System.arraycopy(storage, index - 1, storage, index, size + 1 - index);
        storage[index-1] = r;
    }
}