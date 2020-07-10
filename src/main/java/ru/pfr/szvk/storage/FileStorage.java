package ru.pfr.szvk.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    public void store(MultipartFile file,String typef);
    public Resource loadFile(String filename,String typef);
    public void deleteAll();
    public void init();
    public Stream<Path> loadFiles();
}

