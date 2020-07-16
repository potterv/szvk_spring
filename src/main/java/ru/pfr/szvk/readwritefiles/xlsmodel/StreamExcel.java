package ru.pfr.szvk.readwritefiles.xlsmodel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import ru.pfr.szvk.readwritefiles.fromfms.*;

import ru.pfr.szvk.Employee;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class StreamExcel implements InterfaceExcel {

    public StreamExcel(){

        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        nameFileToFms = String.join("",new File("").getAbsolutePath(),"\\mail\\requests\\toFMS_", new Date().toString().replaceAll("\\s","_").replaceAll(":","_"),".xls");
    }

    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    @Override
    public void writeToXls(List<Employee> employees) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees");

        List<Employee> list = employees;

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // uuidPachki
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("uuidPachki");
        cell.setCellStyle(style);
        // uuidrecord
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("uuidrecord");
        cell.setCellStyle(style);
        // snils
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("snils");
        cell.setCellStyle(style);
        // surname
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("surname");
        cell.setCellStyle(style);
        // name
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("name");
        cell.setCellStyle(style);

        // Patronymic
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Patronymic");
        cell.setCellStyle(style);
        // birthday
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("birthday");
        cell.setCellStyle(style);
        // country
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("country");
        cell.setCellStyle(style);
        // area
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("area");
        cell.setCellStyle(style);
        // region
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("region");
        cell.setCellStyle(style);
        // city
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("city");
        cell.setCellStyle(style);
        // residenceCrimeaFromFms
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("residenceCrimeaFromFms");
        cell.setCellStyle(style);

        // commentsFms
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("commentsFms");
        cell.setCellStyle(style);



        // Data
        for (Employee emp : list) {
            rownum++;
            row = sheet.createRow(rownum);

            // UuidPachka (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(emp.getUuidPachka().toString());
            // UuidRecord (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(emp.getUuidRecord().toString());
            // Snilsy (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(emp.getSnils().toString());
            // Surname (D)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(emp.getSurname().toString());
            // Name (E)

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(emp.getName().toString());

            // Patronymic (F)
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(emp.getPatronymic().toString());
            // Birthday (G)
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(emp.getBirthday().toString());
            // Country (H)
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(emp.getCountry().toString());

            //Area (J)
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(emp.getArea().toString());

            //getRegion (K)
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(emp.getRegion().toString());

            //City (L)
            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(emp.getCity().toString());

        }
        File file = new File(nameFileToFms);
//        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        workbook.close();
        outFile.close();
        log.info(String.join(" ","Created file: -",file.getAbsolutePath()));
//


    }

    @Override
    public List<AdrRowFromFms> readFromXls(String fileNameXlsFromFms) throws IOException {
       log.info(toString().join("","Начата  загрузка данных из файла ",fileNameXlsFromFms," от ФМС"));
        List<Employee> employees =new LinkedList<Employee>();
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File(String.join("",new File("").getAbsolutePath(),"\\mail\\response\\",fileNameXlsFromFms)));
//        FileInputStream inputStream = new FileInputStream(new File(fileNameXlsFromFms));

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();

        List<AdrRowFromFms> rowsfms= new ArrayList<AdrRowFromFms>();

        AdrRowFromFms adrRowFromFms;
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            adrRowFromFms = new AdrRowFromFms();

            adrRowFromFms.setUuidPachki(row.getCell(0).getStringCellValue().toString());
            adrRowFromFms.setUuidRecord(row.getCell(1).getStringCellValue().toString());

            adrRowFromFms.setCountry(row.getCell(7).getStringCellValue().toString());
            adrRowFromFms.setArea(row.getCell(8).getStringCellValue().toString());
            adrRowFromFms.setRegion(row.getCell(9).getStringCellValue().toString());
            adrRowFromFms.setCity(row.getCell(10).getStringCellValue().toString());
            log.info(String.join("","Признак резидента от ФМС ",row.getCell(11).getStringCellValue().toString()));
            if (row.getCell(11).getStringCellValue().toString().equals("да") ){
                adrRowFromFms.setResidentCrimea(true);

            }
            if (row.getCell(11).getStringCellValue().toString().equals("нет")){
                adrRowFromFms.setResidentCrimea(false);
            }

            if (row.getLastCellNum()==12){
                row.createCell(12, CellType.STRING);
//
                adrRowFromFms.setCommentary("-");

            }else {
//                row.createCell(12, CellType.STRING);

                adrRowFromFms.setCommentary(row.getCell(12).getStringCellValue().toString());

            }



            rowsfms.add(adrRowFromFms);

        }
        inputStream.close();
        workbook.close();
        log.info("Загрузка данных от ФМС завершена");
        return  rowsfms;
    }
    private String nameFileToFms;
    public String getNameFileToFms(){
        return nameFileToFms;
    }
    private static final Logger log = Logger.getLogger(StreamExcel.class);
}
