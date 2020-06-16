package ru.pfr.szvk.readwritefiles;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class zipFileTest {

    @Test
    void getTypeFile() {
    }

    @Test
    void write()  {
     String pathD = String.join("",new File("").getAbsolutePath(),"\\mail\\inSZVK");
     ZipFile zf = new ZipFile();
     zf.write(pathD, UUID.randomUUID().toString());

    }

    @Test
    void read() {
    }

    @Test
    void readWriteZip() {
    }
}