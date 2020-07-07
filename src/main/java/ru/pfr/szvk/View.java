package ru.pfr.szvk;

import ru.pfr.szvk.readwritefiles.CsvWriter;
import ru.pfr.szvk.readwritefiles.xlsmodel.*;

import java.io.IOException;
import java.util.List;

public class View {
    public View(List<Employee> employees, CsvWriter csvWriter){
        csvWriter.saveCsv(employees);
    }

    public View(List<Employee> employees, StreamExcel xlsWriter) throws IOException {
        xlsWriter.writeToXls(employees);
        setNameFileXls(xlsWriter);
    }
    private void setNameFileXls(StreamExcel xls){
        this.nameFileXls = xls.getNameFileToFms();
    }

    public String getNameFileXls(){

        return this.nameFileXls;
    }
    private String nameFileXls;
}
