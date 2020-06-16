package ru.pfr.szvk;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class SzvkMainCmd {
    public static void main(String[] args) throws XMLStreamException, IOException, SQLException {
        new Controller();
    }
}
