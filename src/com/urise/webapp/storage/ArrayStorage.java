package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int foundedIndex = getIndexOfUuid(r.getUuid());
        if (foundedIndex == -1) {
            System.out.printf("ERROR: %s uuid does not exist\n", r.getUuid());
            return;
        }
        storage[foundedIndex] = r;
    }

    public void save(Resume r) {
        int foundedIndex = getIndexOfUuid(r.getUuid());
        if (foundedIndex != -1) {
            System.out.printf("ERROR: %s uuid is exist\n", r.getUuid());
            return;
        }
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: storage full\n");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int foundedIndex = getIndexOfUuid(uuid);
        if (foundedIndex == -1) {
            System.out.printf("ERROR: %s uuid does not exist\n", uuid);
            return null;
        }
        return storage[foundedIndex];
    }

    public void delete(String uuid) {
        int deletedIndex = getIndexOfUuid(uuid);
        if (deletedIndex == -1) {
            System.out.printf("ERROR: %s uuid does not exist\n", uuid);
            return;
        }
        storage[deletedIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndexOfUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
