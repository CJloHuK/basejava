package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
//        for (int i = 0; i < size; i++) {
//            storage[i] = null;
//        }
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
//        for (int i = 0; i < size; i++) {
//            if (storage[i].getUuid().equals(r.getUuid())) {
//                storage[i] = r;
//            }
//        }
    }

    public void save(Resume r) {
        int foundedIndex = getIndexOfUuid(r.getUuid());
        if (foundedIndex != -1) {
            System.out.printf("ERROR: %s uuid is exist\n", r.getUuid());
            return;
        }
        if (size == storage.length) {
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
//        for (int i = 0; i < size; i++) {
//            if (storage[i].getUuid().equals(uuid)) {
//                return storage[i];
//            }
//        }
//        return null;
    }

    public void delete(String uuid) {
//        int deleteIndex = -1;
//        for (int i = 0; i < size; i++) {
//            if (storage[i].getUuid().equals(uuid)) {
//                deleteIndex = i;
//                storage[i] = null;
//                size--;
//                break;
//            }
//        }
//        if (deleteIndex >= 0) {
//            if (size - deleteIndex >= 0)
//                System.arraycopy(storage, deleteIndex + 1, storage, deleteIndex, size - deleteIndex);
//            storage[size] = null;
//        }
        int deletedIndex = getIndexOfUuid(uuid);
        if (deletedIndex == -1) {
            System.out.printf("ERROR: %s uuid does not exist\n", uuid);
            return;
        }
        storage[deletedIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
//        for (int i = 0; i < size; i++) {
//            if (storage[i].getUuid().equals(uuid)) {
//                storage[i] = storage[size - i];
//                storage[size - i] = null;
//                size--;
//                break;
//            }
//        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOf(storage, size);
//        Resume[] resumes = new Resume[size];
//        System.arraycopy(storage, 0, resumes, 0, size);
//        return resumes;
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
