package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract Resume doRead(InputStream file) throws IOException;

    protected abstract void doWrite(Resume r, OutputStream file) throws IOException;

    public AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must be not null");
        if (!Files.exists(directory)) {
            throw new IllegalArgumentException(dir + " in not exist");
        }
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " in not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " in not RW");
        }
        this.directory = directory;
    }

    @Override
    protected Resume[] getAll() {
        return getFilesList().map(this::doGet).toArray(Resume[]::new);
    }


    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileName(file), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileName(file), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", getFileName(file), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file, getFileName(file), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected boolean isExist(Path file) {
        //return Files.exists(file);
        return Files.isRegularFile(file);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        //return Paths.get(directory.toString(), uuid);
        return directory.resolve(uuid);
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

}
