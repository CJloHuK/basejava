package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializestrategy.SerializeStrategy;

import java.io.*;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private SerializeStrategy strategy;
    private File directory;

    public FileStorage(File directory, SerializeStrategy strategy) {
        Objects.requireNonNull(directory, "directory must be not null");
        this.strategy = strategy;
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in not RW");
        }
        this.directory = directory;
    }

    @Override
    protected Resume[] getAll() {
        File[] list = directory.listFiles();
        if (list != null) {
            Resume[] resumes = new Resume[list.length];
            for (int i = 0; i < list.length; i++) {
                resumes[i] = doGet(list[i]);
            }
            return resumes;
        } else throw new StorageException("Directory read error");
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error");
        }
        return list.length;
    }
}
