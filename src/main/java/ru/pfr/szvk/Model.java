package ru.pfr.szvk;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import ru.pfr.szvk.readwritefiles.CsvWriter;
import ru.pfr.szvk.readwritefiles.ReadDerectory;
import ru.pfr.szvk.readwritefiles.StaxStreamProcessor;
import ru.pfr.szvk.readwritefiles.fromfms.RowFromFms;
import ru.pfr.szvk.readwritefiles.xlsmodel.StreamExcel;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Model {
    public Model(){

    }


//    public List<Employee> getEmployee(DbHandler dbHandler) throws IOException, XMLStreamException, SQLException {
//        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
//
//        String pathD = "D:\\IdeaProject\\szvk\\mail\\inSZVK";
//        log.info(String.join(" ", "Определен mail каталог", pathD));
//        ReadDerectory rf= ReadDerectory.getInstance();
//        log.info(String.join(" ", "файлы для обработки определены"));
//        StaxStreamProcessor staxStreamProcessor = new StaxStreamProcessor();
//        log.info(String.join(" ", "Инициализирован класс StaxStreamProcessor"));
//
//
//        List<Employee> employees = new ArrayList<Employee>();
//        log.info(String.join(" ", "Инициализирован список employees"));
//
//        for (StringBuffer file:rf.getListFiles(pathD)) {
//            staxStreamProcessor.readXml(file.toString());
//            log.info(String.join(" ", "обрабатывается файл", file.toString()));
//            this.addDateToTable(dbHandler,staxStreamProcessor.getAllEmployee());
//            log.info(String.join(" ", "В таблицу employees_from_policyholder добавлены записи из xml файлов"));
////            for (Employee employee:staxStreamProcessor.getAllEmployee()) {
////                LinkedHashMap linkedHashMapParam = new LinkedHashMap();
////                linkedHashMapParam.put("snils",employee.getSnils().toString());
////                linkedHashMapParam.put("country","");
////                linkedHashMapParam.put("area","");
////                linkedHashMapParam.put("region","");
////                linkedHashMapParam.put("city","");
////                Employee employeedb =dbHandler.getHumen("EMPLOYEES_FROM_MIC","snils",linkedHashMapParam);
////                employee.setCountry(employeedb.getCountry());
////                employee.setArea(employeedb.getArea());
////                employee.setRegion(employeedb.getRegion());
////                employee.setCity(employeedb.getCity());
////                employees.add(employee);
////                log.info(String.join(" ", "В employees добавлена запись"));
////
////            }
//        }
//
//
//
////        dbHandler.close();
//        Collections.sort(employees);
//        return employees;
//    }


    public void readDataFromXmlToDb(DbHandler dbHandler) throws IOException, XMLStreamException, SQLException {
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");

        String pathD = "D:\\IdeaProject\\szvk\\mail\\inSZVK";
        log.info(String.join(" ", "Определен mail каталог", pathD));
        ReadDerectory rf= ReadDerectory.getInstance();
        log.info(String.join(" ", "файлы для обработки определены"));
        StaxStreamProcessor staxStreamProcessor = new StaxStreamProcessor();
        log.info(String.join(" ", "Инициализирован класс StaxStreamProcessor"));
        this.uuidPachki = staxStreamProcessor.getUuidPachki();

        List<Employee> employees = new ArrayList<Employee>();
        log.info(String.join(" ", "Инициализирован список employees"));

        for (StringBuffer file:rf.getListFiles(pathD)) {

            staxStreamProcessor.readXml(file.toString());
            log.info(String.join(" ", "обрабатывается файл", file.toString()));
            for (Employee employee:staxStreamProcessor.getAllEmployee()
                 ) {
                employees.add(employee);
            }

//            System.out.println(staxStreamProcessor.getAllEmployee().toString());
//            this.addDataFromPoliciholder(dbHandler,staxStreamProcessor.getAllEmployee());
            log.info(String.join(" ", "В таблицу employees_from_policyholder добавлены записи из xml файлов"));

        }

        System.out.println(employees.size());
        for (Employee e: employees
             ) {
            System.out.println(e.getSnils());
        }

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
        employees = dbHandler.getEmployees("view_employee_with_adress","UUID_P",param);
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

    public DbHandler getConnectDb(){
        try {
                    log.info(String.join(" ", "Инициализирован класс  DbHandler, Выполнено подключение к базе"));
            return DbHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
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
            dbHandler.addData("EMPLOYEES_FROM_POLICYHOLDER_test","snils",param);
            param.clear();
        }
    }

    public void loadDataFromFms(DbHandler dbHandler, List<RowFromFms> rows) throws  SQLException {


        LinkedHashMap param = new LinkedHashMap();


        for (RowFromFms row:rows) {

//            param.put("snils",UUID.randomUUID().toString());
            param.put("uuid_P",row.getUuidPachki());
            param.put("uuid_R",row.getUuidRecord());
            param.put("Resident_Crimea",row.isResidentCrimea());
            param.put("commentary",row.getCommentary());
            param.put("date_load_file_from_fms_xls",LocalDate.now().toString());
            dbHandler.addData("FMS_DATA","uuid_R",param);
            param.clear();
        }

    }
    private String uuidPachki;
    private static final Logger log = Logger.getLogger(Model.class);
}
