package ru.pfr.szvk;

import org.junit.Test;

import ru.pfr.szvk.readwritefiles.xlsmodel.StreamExcel;


import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class modelTest {

    @Test
    public void print() throws IOException, XMLStreamException, SQLException {
        Model m = new Model();
        DbHandler dbHandler = DbHandler.getInstance();
        dbHandler.setConnection();
        m.readDataFromXmlToDb(dbHandler);
//        m.getCsv().saveCsv(m.getEmployeeList(dbHandler));
        m.getXls().writeToXls(m.getEmployeeList(dbHandler));

    }
    @Test
    public void loadDataFromFms() throws SQLException, IOException {
        StreamExcel streamExcel = new StreamExcel();

        Model m = new Model();
        DbHandler dbHandler = DbHandler.getInstance();
        dbHandler.setConnection();
        m.loadDataFromFms(dbHandler,streamExcel.readFromXls("Проверка УВМ 01_06_2020 со СНИЛС.xls"));
    }
}