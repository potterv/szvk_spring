package ru.pfr.szvk.readwritefiles;

import org.dom4j.DocumentException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

interface TypeFile {
    void getTypeFile();

    public void write(String filename, String uuidPachki) throws XMLStreamException, IOException;

    void read(String fileName) throws DocumentException, IOException, XMLStreamException;
}