package ru.pfr.szvk;

import ru.pfr.szvk.readwritefiles.CsvWriter;
import ru.pfr.szvk.readwritefiles.xlsmodel.*;

import java.io.IOException;
import java.util.List;

public class View {
    View(List<Employee> employees, CsvWriter csvWriter){
        csvWriter.saveCsv(employees);
    }

    View(List<Employee> employees, StreamExcel xlsWriter) throws IOException {
        xlsWriter.writeToXls(employees);
    }

}
