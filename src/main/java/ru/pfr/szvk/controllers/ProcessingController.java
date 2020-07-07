package ru.pfr.szvk.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.szvk.DbHandler;
import ru.pfr.szvk.MediaType.MediaTypeUtils;
import ru.pfr.szvk.View;
import ru.pfr.szvk.WraperM;
import ru.pfr.szvk.readwritefiles.ReadDerectory;

import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

//import ru.pfr.szvk.models.Roles;
//import ru.pfr.szvk.repo.RolesRepository;

@Controller
public class ProcessingController {
    public ProcessingController(){
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        this.wraperM = new WraperM();
        this.dbHandler = this.wraperM.getModel().getConnectDb();
    }

//    @GetMapping("/processing")
//    public String processing(Model model)  {
//
//
//        return "processing";
//    }

    @GetMapping("/processing")
    @PostMapping("/processing")
    public String processingPost(Model model){
         log.info("Старт обработки");


        try {

            this.wraperM.getModel().readDataFromXmlToDb(this.dbHandler);
//            this.wraperM.getModel().delete(ReadDerectory.PATHINSZVK);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        log.info("Окончание обработки");
        return "redirect:/download";
    }

    private static final String DIRECTORY = "d:\\IdeaProject\\szvk_spring\\mail\\requests\\";
    private static final String  DEFAULT_FILE_NAME ="";

    @Autowired
    private ServletContext servletContext;

    // http://localhost:8080/download1?fileName=abc.zip
    // Using ResponseEntity<InputStreamResource>
    @RequestMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
//        System.out.println("fileName: " + fileName);
//        System.out.println("mediaType: " + mediaType);

        this.view=this.wraperM.setView(this.wraperM.getModel().getEmployeeList(this.dbHandler),this.wraperM.getModel().getXls());

        try {
            this.dbHandler.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.nameFile=this.view.getNameFileXls();

        fileName = this.nameFile;
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        log.info(String.join(" ","Файл - ",fileName, "скачен"));
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

//    public static String getNameFile() {
//        return nameFile;
//    }
    private View view;
    private WraperM wraperM;
    private  String nameFile;
    private DbHandler dbHandler;
    private static final Logger log = Logger.getLogger(ProcessingController.class);
}
