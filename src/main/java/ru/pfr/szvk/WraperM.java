package ru.pfr.szvk;

import ru.pfr.szvk.readwritefiles.xlsmodel.StreamExcel;

import java.io.IOException;
import java.util.List;

public class WraperM {
   private Model model = new Model();

    public Model getModel() {
        return this.model;
    }
    public View setView(List<Employee> employees, StreamExcel xlsWriter) throws IOException {
        return new View(employees,xlsWriter);
    }

}
