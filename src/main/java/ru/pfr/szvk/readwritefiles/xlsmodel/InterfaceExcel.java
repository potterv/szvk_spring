package ru.pfr.szvk.readwritefiles.xlsmodel;


import ru.pfr.szvk.readwritefiles.fromfms.*;
import ru.pfr.szvk.Employee;

import java.io.IOException;
import java.util.List;

interface InterfaceExcel {
     void writeToXls (List<Employee> employees) throws IOException;
     List<AdrRowFromFms> readFromXls(String fileNameXlsFromFms) throws IOException;
}
