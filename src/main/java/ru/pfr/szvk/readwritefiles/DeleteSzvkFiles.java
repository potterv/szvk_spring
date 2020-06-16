package ru.pfr.szvk.readwritefiles;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class DeleteSzvkFiles {
   public DeleteSzvkFiles(){
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
    }

    public void deleteFiles (String path){

        File[] files = new File(path).listFiles();
        for (File file:files
             ) {
            if (file.delete()){
                log.info(String.join("","Файл СЗВ-К ",file.getName()," удален из папки ",String.join("",path)));

            }else{
                log.info(String.join("","Файл СЗВ-К ",file.getName()," не удален из папки ",String.join("",path)));
            }
        }
    }
    private static final Logger log = Logger.getLogger(DeleteSzvkFiles.class);
}
