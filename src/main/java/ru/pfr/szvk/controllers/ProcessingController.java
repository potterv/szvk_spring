package ru.pfr.szvk.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pfr.szvk.DbHandler;
import ru.pfr.szvk.WraperM;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

//import ru.pfr.szvk.models.Roles;
//import ru.pfr.szvk.repo.RolesRepository;

@Controller
public class ProcessingController {

//    @GetMapping("/processing")
//    public String processing(Model model)  {
//
//
//        return "processing";
//    }
    @GetMapping("/processing")
    @PostMapping("/processing")
    public String processingPost(){
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        log.info("Старт обработки");
        WraperM wraperM = new WraperM();
        DbHandler dbHandler = wraperM.getModel().getConnectDb();
        try {

            wraperM.getModel().readDataFromXmlToDb(dbHandler);
            wraperM.setView(wraperM.getModel().getEmployeeList(dbHandler),wraperM.getModel().getXls());

            dbHandler.getConnection().close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        log.info("Окончание обработки");
        return "redirect:/";
    }

    private DbHandler dbHandler;
    private static final Logger log = Logger.getLogger(ProcessingController.class);
}
