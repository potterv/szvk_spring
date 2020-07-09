package ru.pfr.szvk.readwritefiles;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.lang.annotation.Documented;
import java.util.Date;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipFile implements TypeFile{

    public ZipFile(){
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        this.pathD = new File("").getAbsolutePath();

    }

    public void getTypeFile(){

        System.out.println("тип файла zip");
    }

    @Override
    public void write(String path,String uuidPachki)  {
//      Список файлов  для архивации
        File[] files = new File(path).listFiles();
        String nameArch=String.join("","SEV_PFR_SZVK_",uuidPachki,"_",new Date().toString().replaceAll("\\s","_").replaceAll(":","_"),".zip");
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(String.join("",this.pathD,"/mail/worked/",nameArch)));

        )
        {
            for (File file: files
            ) {
                FileInputStream fis= new FileInputStream(String.join("/",path,file.getName()));
                ZipEntry entry1=new ZipEntry(file.getName().toString());
//                System.out.printf(file.getName().toString());
                zout.putNextEntry(entry1);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                fis.close();
                log.info(String.join("","Файл СЗВ-К ",file.getName()," добавлен в архив ",nameArch));
//                System.out.println(file.getAbsolutePath());

            }

            zout.closeEntry();
              zout.close();

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

        finally {
            // закрываем текущую запись для новой записи

        }



    }
    @Override
    public void read(String fileName){
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(String.join("",this.pathD,"\\in\\zip\\",fileName))))
        {
            ZipEntry entry;
            String name;
            long size;

            final File dir1 = new File(String.join("",this.pathD,"\\in\\dir\\",fileName.substring(0,fileName.length()-4)));
            if(!dir1.exists()) {
                if(dir1.mkdir()) {
                    System.out.println("Kaтaлoг " + dir1.getAbsolutePath()+ " ycпeшнo coздaн.");
                }
                else {
                    System.out.println("Kaтaлoг " + dir1.getAbsolutePath()+ " coздвть нe yдaлocь.");
                }
            } else { System.out.println("Kaтaлoг " + dir1.getAbsolutePath()+ " yжe cyщecтвyeт.");
            }
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                size=entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                // распаковка
                FileOutputStream fout = new FileOutputStream(String.join("",this.pathD,"\\in\\dir\\",fileName.substring(0,fileName.length()-4),"\\",name));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

//    public void readWriteZip(){
//        final String PATHDIRIN = String.join("",pathD,"\\in");
//        final String PATHDIROUT = String.join("",pathD,"\\out");
//        final String PATHZIP =String.join("",PATHDIRIN,"\\zip");
//        final String PATHDIR = String.join("",PATHDIRIN,"\\dir");
//        File dirZip = new File(PATHZIP);
//        File[] filesZip = dirZip.listFiles();
//        File dirDir = new File(PATHZIP);
//        File[] filesDir = dirDir.listFiles();
//
//        for (File fileZip: filesZip
//        ) {
//            this.read(fileZip.getName());
//
//            this.write(String.join("",new File("").getAbsolutePath(),"\\mail\\worked\\pfr_szvk_", new Date().toString().replaceAll("\\s","_").replaceAll(":","_"),".zip"));
//            System.out.println(fileZip.getAbsolutePath());
//        }
//        System.out.printf("");
//    }

    private String pathD;
    private static final Logger log = Logger.getLogger(ZipFile.class);
}