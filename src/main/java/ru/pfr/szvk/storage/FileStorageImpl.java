package ru.pfr.szvk.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageImpl implements FileStorage{

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("mail\\");
    private enum TypeFile { xml,xls }
    private String pathIn="inSZVK\\";
    private String pathResponse="response\\";
    @Override
    public void store(MultipartFile file,String fileType){
        try {
             if (fileType.equals("xml")){
                 Files.copy(file.getInputStream(), this.rootLocation.resolve(String.join("",pathIn,file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
             }
             if (fileType.equals("xls")){
                 Files.copy(file.getInputStream(), this.rootLocation.resolve(String.join("",pathResponse,file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
             }

        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    @Override
    public Resource loadFile(String filename,String fileType) {
        try {
            Path file;
            Resource resource;
            if (fileType.equals("xml")){
                file = rootLocation.resolve(String.join("",pathIn,filename));
                resource = new UrlResource(file.toUri());
                if(resource.exists() || resource.isReadable()) {
                    return resource;
                }else{
                    throw new RuntimeException("FAIL!");
                }
            }
            if (fileType.equals("xls")) {
                file = rootLocation.resolve(String.join("", pathResponse, filename));
                resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("FAIL!");
                }
            }



        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    @Override
    public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored file");
        }
    }
}
