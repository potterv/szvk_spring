package ru.pfr.szvk.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.szvk.DbHandler;
import ru.pfr.szvk.WraperM;
import ru.pfr.szvk.readwritefiles.xlsmodel.StreamExcel;
import ru.pfr.szvk.storage.FileStorage;


@Controller
public class UploadFileController {

    public UploadFileController(){
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
    }

    @Autowired
    FileStorage fileStorage;

    @GetMapping("/uploadform")
    public String index() {
        return "uploadform";
    }

    @PostMapping("/uploadform")
    public String uploadMultipartFile(@RequestParam("files") MultipartFile[] files, Model model) {
        List<String> fileNames = null;
        log.info("Загроузка файлов на сервер начата");
        try {
            fileNames = Arrays.asList(files)
                    .stream()
                    .map(file -> {
                        fileStorage.store(file,"xml");
                        return file.getOriginalFilename();
                    })
                    .collect(Collectors.toList());

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
            log.info("Загроузка файлов на сервер выполнена успешно");
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
            log.info("Загроузка файлов на сервер не выполнена");
        }
        log.info("Загроузка файлов на сервер завершена");
        return "uploadform";
    }

//    @GetMapping("/uploadformXls")
//    public String uploadformXls() {
//        return "uploadformXls";
//    }
//
//    @PostMapping("/uploadformXls")
//    public String uploadMultipartFileXls(@RequestParam("files") MultipartFile[] files, Model model) {
//        List<String> fileNames = null;
//        log.info("Загроузка файла от ФМС на сервер начата");
//        try {
//            fileNames = Arrays.asList(files)
//                    .stream()
//                    .map(file -> {
//                        fileStorage.store(file,"xls");
//                        return file.getOriginalFilename();
//                    })
//                    .collect(Collectors.toList());
//
//            model.addAttribute("message", "Files uploaded successfully!");
//            model.addAttribute("files", fileNames);
//            this.dbHandler = this.wraperM.getModel().getConnectDb();
//            if (fileNames.size()==1){
//                this.wraperM.getModel().loadDataFromFms(dbHandler, this.streamExcel.readFromXls(fileNames.get(0)));
//            }else{
//                log.info("Вы не выбрали файл для загрузки");
//            }
//
//            log.info("Загроузка файла от ФМС на сервер выполнена успешно");
//        } catch (Exception e) {
//            model.addAttribute("message", "Fail!");
//            model.addAttribute("files", fileNames);
//            log.info("Загроузка файлов на сервер не выполнена");
//        }
//        log.info("Загроузка файла от ФМС на сервер завершена");
//        return "uploadformXls";
//    }
//    private DbHandler dbHandler;
//    private WraperM wraperM;
//    private StreamExcel streamExcel = new StreamExcel();
    private static final Logger log = Logger.getLogger(UploadFileController.class);
}
