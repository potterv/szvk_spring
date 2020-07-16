package ru.pfr.szvk;

//import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
//import org.sqlite.JDBC;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class DbHandler {


    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    public Connection getConnection() {
        return this.connection;
    }
    public void setConnection(){
        this.connection = null;
        try {

            Class.forName(jdbcClassName);
            log.info("Драйвер для работы с базой зарегистрирован");

            this.connection = DriverManager.getConnection(url, user, password);
            log.info(this.connection.getMetaData().toString());
            log.info("Подключение к базе  выполнено");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (this.connection != null) {
                log.info("Connected successfully.");


            }
        }
    }
    private PreparedStatement pstmt = null;
    /**
     * @throws SQLException
     */

    private DbHandler() throws SQLException {
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
         this.jdbcClassName="com.ibm.db2.jcc.DB2Driver";
         this.url="jdbc:db2://10.92.0.71:50000/szvk";
         this.user="db2admin";
         this.password="dB2@dm1n";




    }
    /**
     * Метод для получения записей из таблицу view_employee_with_adress с параметрами
     *
     */
    public List<Employee> getAllEployees() {
        String snils ="";
        String country = "";
        String area ="";
        String region = "";
        String city = "";
        String uuidpachki="";
        String uuidRecor ="";
        String surname ="";
        String name ="";
        String patronymic="";
        String birthday = "";

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()  ) {

            // В данный список будем загружать людей, полученных из БД
            List<Employee> employees = new ArrayList<Employee>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
//            ResultSet resultSet = statement.executeQuery("SELECT snils, country, area, region, city  FROM HUMEN");
            this.pstmt=this.connection.prepareStatement("SELECT distinct snils,uuid_p, uuid_r,surname, name, country, patronymic,birthday, area, region, city  FROM db2admin.view_employee_with_adress order by surname");
//            pstmt.setObject(1,snils);
            ResultSet resultSet = pstmt.executeQuery();
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                    snils = resultSet.getString("snils");

                    country = resultSet.getString("country");
                if (country== null){
                    country ="-";
                }
                    area = resultSet.getString("area");
                if (area==null){
                    area ="-";
                }
                region =resultSet.getString("region");
                if (region==null){
                    region ="-";
                }
                city = resultSet.getString("city");
                if (city==null){
                    city ="-";
                }
                employees.add(new Employee.Builder(new StringBuffer(snils)).getPolicyholder(
                        new StringBuffer(resultSet.getString("uuid_p")),
                        new StringBuffer(resultSet.getString("uuid_r")),
                        new StringBuffer(resultSet.getString("surname")),
                        new StringBuffer(resultSet.getString("name")),
                        new StringBuffer(resultSet.getString("patronymic")),
                        LocalDate.parse(resultSet.getString("birthday")),
                        new StringBuffer(country),
                        new StringBuffer(area),
                        new StringBuffer(region),
                        new StringBuffer(city)).buidl());

            }
            log.info("Метод getAllHumen вернул список");
            // Возвращаем наш список
            return employees;
        } catch (SQLException e) {
//            e.printStackTrace();
            log.error("Это сообщение ошибки, Метод getAllHumen вернул пустой список");
            log.error(new String(e.getSQLState()));
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    /**
     * Метод для выборки информации из таблицы
     * @param nameTable - имя таблицы
     * @param nameColl - слоаврь наименований палей и их значений в таблице
     * @param param - слоаврь наименований палей и их значений в таблице
     */
    //
    public Employee getHumen(String nameTable, String nameColl, LinkedHashMap param) {

        boolean isdDate=false;
        String country = "-";
        String area ="-";
        String region = "-";
        String city = "-";
        String calls =  param.keySet().toString().replaceAll("\\["," ").replaceAll("]"," ");
        String vallColl = param.get(nameColl).toString();

        String sql = "".join("",
                "SELECT ",calls,
                " FROM db2admin.",nameTable," WHERE ",nameColl,"=?");

        try (  PreparedStatement statement = this.connection.prepareStatement(sql))
        {
            statement.setObject(1, vallColl);
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery();

            log.info("Данные из базы получены");
            if (resultSet.next()) {
                if (resultSet.getString("snils")!=null){
                    isdDate =true;
                }
                country = resultSet.getString("country");
                if (country == null) {
                    country = "-";
                }
                area = resultSet.getString("area");
                if (area == null) {
                    area = "-";
                }
                region = resultSet.getString("region");
                if (region == null) {
                    region = "-";
                }
                city = resultSet.getString("city");
                if (city == null) {
                    city = "-";
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка доступности данных");
//            log.error("Это сообщение ошибки, Метод findHumen вернул пустой список");
            log.error(new String(e.getSQLState()));
            log.error(e.getStackTrace().toString());
        }
        finally {
            if (isdDate){

                return new Employee.Builder(new StringBuffer(param.get(nameColl).toString())).getPFR(
                    new StringBuffer(country),
                    new StringBuffer(area),
                    new StringBuffer(region),
                    new StringBuffer(city)).buidl();

            }else {
                log.warn("".join(" ",param.get(nameColl).toString(), " в базе данный снилс не найден"));
                return new Employee.Builder(new StringBuffer(param.get(nameColl).toString())).getPFR(
                        new StringBuffer("-"),
                        new StringBuffer("-"),
                        new StringBuffer("-"),
                        new StringBuffer("-")).buidl();
            }
        }

    }

    /**
     * Метод для получения записей в таблицу с параметрами
     * @param nameTable - имя таблицы
     * @param nameColl - Имя поля в таблице по кторому выполняется фильтр
     */
    // поиск информации о человеке по СНИЛС
    public List<Employee> getEmployees(String nameTable, String nameColl, LinkedHashMap param) {
        List<Employee> employeeList = new LinkedList<Employee>();
//        employeeList = null;
        boolean isdDate=false;
        String snils ="";
        String country = "-";
        String area ="-";
        String region = "-";
        String city = "-";
        String commentary = "-";

        String calls =  param.keySet().toString().replaceAll("\\["," ").replaceAll("]"," ").replaceAll(" ","");


        String vallColl = param.get(nameColl).toString();

        String sql = "".join("",
                "SELECT distinct ",calls,
                " FROM db2admin.",nameTable," WHERE ",nameColl,"=? order by surname");

        String nameColls[] = calls.split(",");


        try (  PreparedStatement statement = this.connection.prepareStatement(sql))
        {
            statement.setObject(1, vallColl);
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery();

            log.info("Данные из базы получены");

           while (resultSet.next()) {

                if (resultSet.getString(nameColl)!=null){
                    isdDate =true;
                }

                snils = resultSet.getString("snils");

                country = resultSet.getString(nameColls[7].toString());
                if (country == null) {
                    country = "-";
                }
                area = resultSet.getString(nameColls[8].toString());
                if (area == null) {
                    area = "-";
                }
                region = resultSet.getString(nameColls[9].toString());
                if (region == null) {
                    region = "-";
                }
                city = resultSet.getString(nameColls[10].toString());
                if (city == null) {
                    city = "-";
                }

//
//               if (nameTable.equals("view_for_Zablag")){
//                   commentary = resultSet.getString("COMMENTARY");
//                  if (commentary == null) {
//                      commentary = "-";
//                   }
//                   employeeList.add(new Employee.Builder(new StringBuffer(resultSet.getString("snils"))).getPolicyholder(
//
//                           new StringBuffer(resultSet.getString(nameColls[1].toString())),
//                           new StringBuffer(resultSet.getString(nameColls[2].toString())),
//                           new StringBuffer(resultSet.getString(nameColls[3].toString())),
//                           new StringBuffer(resultSet.getString(nameColls[4].toString())),
//                           new StringBuffer(resultSet.getString(nameColls[5].toString())),
//                           LocalDate.parse(resultSet.getString("birthday")),
//                           new StringBuffer(country),
//                           new StringBuffer(area),
//                           new StringBuffer(region),
//                           new StringBuffer(city),
//                           new StringBuffer(resultSet.getString(nameColls[11].toString()).toString()),
//                           new StringBuffer(commentary)
//                   ).buidl());
//               }

//               if (nameTable.equals("VIEW_UNICAL_SNILS")){
                   employeeList.add(new Employee.Builder(new StringBuffer(resultSet.getString("snils"))).getPolicyholder(

                           new StringBuffer(resultSet.getString(nameColls[1].toString())),
                           new StringBuffer(resultSet.getString(nameColls[2].toString())),
                           new StringBuffer(resultSet.getString(nameColls[3].toString())),
                           new StringBuffer(resultSet.getString(nameColls[4].toString())),
                           new StringBuffer(resultSet.getString(nameColls[5].toString())),
                           LocalDate.parse(resultSet.getString("birthday")),
                           new StringBuffer(country),
                           new StringBuffer(area),
                           new StringBuffer(region),
                           new StringBuffer(city)

                   ).buidl());
//               }


           }

           return employeeList;

        } catch (SQLException e) {
            log.error("Ошибка доступности данных");
            log.error(e.getSQLState());
            log.error(e.getStackTrace());
            return Collections.emptyList();
        }
    }



    /**
     * Метод для добавления записей в таблицу с параметрами
     * @param nameTable - имя таблицы
     * @param keyControl  - наименование поля для проверки  загружена ли запись ранее
     * @param nameColls - слоаврь наименований палей и их значений в таблиц
     */
    public void addData(String nameTable, String keyControl , LinkedHashMap nameColls) throws SQLException {
        StringBuffer volue = new StringBuffer();

        try (PreparedStatement statement = this.connection.prepareStatement("".join("", "SELECT  distinct ", keyControl, ", UUID_R", " FROM db2admin.", nameTable, " Where ",keyControl," =?")))

         {

            statement.setObject(1,nameColls.get(keyControl).toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                for (int countValue = 0; countValue<nameColls.size();countValue++){

                    volue.append("?");
                        if (countValue+1<nameColls.size()){
                            volue.append(",");
                        }
                }
                try (PreparedStatement statement1 = this.connection.prepareStatement("".join(" ",
                        "INSERT INTO db2admin.",
                        nameTable,
                        nameColls.keySet().toString().replaceAll("\\[","(").replaceAll("]",")"),
                        "VALUES (",
                        volue,
                        ")"

                        )))
                     {
                    for (int countValue = 0; countValue<nameColls.size();countValue++){
                      statement1.setObject(countValue+1,  nameColls.values().toArray()[countValue].toString());
                    }
                    statement1.executeUpdate();
                    log.info("".join("","В таблицу ", nameTable," добавлена запись с uuid ",nameColls.get("uuid_R").toString()));


                }catch (Exception e){

                    log.error(e.getMessage());
                    log.error(e.getStackTrace().toString());

                    log.info("".join("","В таблицу ", nameTable," не добавлена запись с uuid ",nameColls.get("uuid_R").toString()));

                }
            } else {

                if(keyControl.equals("snils")){

                    log.warn("".join(" ", "Запись с "," UUID_R = ", resultSet.getString("UUID_R")," СНИЛС - ",nameColls.get(keyControl).toString()," существует в базе и  не будет добавлена в таблицу", nameTable));
                }else{
                    log.warn("".join(" ", "Запись с "," UUID_R = ", resultSet.getString("UUID_R"),"  не будет добавлена в таблицу", nameTable));
                }

            }

        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
        }


    }

    public void close() throws SQLException {

        this.connection.close();
    }

    private String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
    private String url="jdbc:db2://10.92.0.71:50000/szvk";
    private String user="db2admin";
    private String password="dB2@dm1n";
    private static final Logger log = Logger.getLogger(DbHandler.class);


}
