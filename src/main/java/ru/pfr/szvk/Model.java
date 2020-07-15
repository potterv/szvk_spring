package ru.pfr.szvk;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import ru.pfr.szvk.readwritefiles.*;
import ru.pfr.szvk.readwritefiles.fromfms.AdrRowFromFms;
import ru.pfr.szvk.readwritefiles.fromfms.RowFromFms;
import ru.pfr.szvk.readwritefiles.xlsmodel.StreamExcel;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Model {
    public Model(){

    }


    public void readDataFromXmlToDb(DbHandler dbHandler) throws IOException, XMLStreamException, SQLException {
        PropertyConfigurator.configure(String.join("",new File("").getAbsolutePath(),"\\src\\main\\resources\\log4j.properties"));

        String pathD = this.pathFromToZip;
        log.info(String.join(" ", "Определен mail каталог", pathD));
        ReadDerectory rf= ReadDerectory.getInstance();
        log.info(String.join(" ", "файлы для обработки определены"));
        StaxStreamProcessor staxStreamProcessor = new StaxStreamProcessor();
        log.info(String.join(" ", "Инициализирован класс StaxStreamProcessor"));
        this.uuidPachki = staxStreamProcessor.getUuidPachki();
        List<StringBuffer> lisFiles = rf.getListFiles(pathD);
        List<Employee> employees = new ArrayList<Employee>();
        log.info(String.join(" ", "Инициализирован список employees"));

        for (StringBuffer file:lisFiles) {

            staxStreamProcessor.readXml(file.toString());
            log.info(String.join(" ", "обрабатывается файл", file.toString()));
            for (Employee employee:staxStreamProcessor.getAllEmployee()
                 ) {
                employees.add(employee);
            }

            log.info(String.join(" ", "Из xml файлов данные прочитаны во временное хранилище"));

        }
        this.addDataFromPoliciholder(dbHandler,employees);
        log.info(String.join(" ", "В таблицу employees_from_policyholder добавлены записи из xml файлов за исключением уже существующих в базе"));
        log.info(String.join("","Количество полученых СНИЛС - ",String.valueOf(employees.size())));

        this.toArchiv(pathD);
        this.delete(pathD);
//        dbHandler.close();

    }

    public List<Employee> getEmployeeList(DbHandler dbHandler){

        LinkedHashMap param = new LinkedHashMap();
        param.put("snils","");
        param.put("uuid_P",this.uuidPachki);
        param.put("uuid_R","");
        param.put("surname","");
        param.put("name","");
        param.put("patronymic","");
        param.put("birthday","");
//        param.put("residenceCrimea","");
        param.put("country","");
        param.put("area","");
        param.put("region","");
        param.put("city","");
//        param.put("numberInsured","");
//        param.put("nameInsured","");
        List<Employee> employees = new LinkedList<Employee>();
        employees = dbHandler.getEmployees("VIEW_UNICAL_SNILS","UUID_P",param);
//        Collections.sort(employees);
        return employees;
    }

    public List<Employee> getAllEmployees(DbHandler dbHandler){

        return  dbHandler.getAllEployees();
    }

    public CsvWriter getCsv(){

        return new CsvWriter();
    }

    public StreamExcel getXls(){

        return new StreamExcel();
    }

    public DbHandler getConnectDb()  {


        try {
            log.info(String.join(" ", "Инициализирован класс  DbHandler, Выполнено подключение к базе"));
            return DbHandler.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public void setConnectionDB(){
        try {
            DbHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public Connection getConnaction(){

        return null;
    }
    public void addDataFromPoliciholder(DbHandler dbHandler, List<Employee> employeeList) throws SQLException {
        LinkedHashMap param = new LinkedHashMap();
        for (Employee employee:employeeList) {


            param.put("uuid_P",employee.getUuidPachka());
            param.put("uuid_R",employee.getUuidRecord());
            param.put("snils",employee.getSnils());
            param.put("surname",employee.getSurname());
            param.put("name",employee.getName());
            param.put("patronymic",employee.getPatronymic());
            param.put("birthday",employee.getBirthday().toString());
            param.put("residenceCrimea",employee.getResidenceCrimea());
            param.put("date_load_file_xml", LocalDate.now().toString());
//            param.put("country",employee.getCountry());
//            param.put("area",employee.getArea());
//            param.put("region",employee.getRegion());
//            param.put("city",employee.getCity());
//            param.put("numberInsured",employee.getRegnumber().toString());
//            param.put("nameInsured",employee.getPolicyholderShort());
//            System.out.println(param.values().toString());
            dbHandler.addData("EMPLOYEES_FROM_POLICYHOLDER","snils",param);
            param.clear();
        }
    }

    public void loadDataFromFms(DbHandler dbHandler, List<AdrRowFromFms> rows) throws  SQLException {


        LinkedHashMap param = new LinkedHashMap();


        for (AdrRowFromFms row:rows) {

////            param.put("snils",UUID.randomUUID().toString());
            param.put("uuid_P",row.getUuidPachki());
            param.put("uuid_R",row.getUuidRecord());
            param.put("Resident_Crimea",row.isResidentCrimea());
            param.put("commentary",row.getCommentary());
            param.put("date_load_file_from_fms_xls",LocalDate.now().toString());
            dbHandler.addData("FMS_DATA","uuid_R",param);
            param.clear();

            param.put("uuid_P",row.getUuidPachki());
            param.put("uuid_R",row.getUuidRecord());
            param.put("country",row.getCountry());
            param.put("area",row.getArea());
            param.put("region",row.getRegion());
            param.put("city",row.getCity());
            param.put("date_load_file_from_fms_xls",LocalDate.now().toString());
            dbHandler.addData("ADRESS","uuid_R",param);
            param.clear();
        }
        this.toArchiv(this.pathFromXlsToZip);
        this.delete(this.pathFromXlsToZip);

    }

    private void toArchiv(String path){
        ZipFile toZip = new ZipFile();
        toZip.write(path,this.uuidPachki);


    }
    private void delete(String path){
        DeleteSzvkFiles delSzvk =new DeleteSzvkFiles();
        delSzvk.deleteFiles(path);
    }

    public String getPathFromToZip() {
        return pathFromToZip;
    }

    private String pathFromToZip = String.join("",new File("").getAbsolutePath(),"\\mail\\inSZVK");
    private String pathFromXlsToZip = String.join("",new File("").getAbsolutePath(),"\\mail\\response");
    private String uuidPachki;
    private static final Logger log = Logger.getLogger(Model.class);
}
