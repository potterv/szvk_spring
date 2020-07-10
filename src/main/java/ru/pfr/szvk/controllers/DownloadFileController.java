package ru.pfr.szvk.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.pfr.szvk.MediaType.MediaTypeUtils;
import ru.pfr.szvk.storage.FileInfo;
import ru.pfr.szvk.storage.FileStorage;

import javax.servlet.ServletContext;


@Controller
public class DownloadFileController {

    @Autowired
    FileStorage fileStorage;

    /*
     * Retrieve Files' Information
     */
    @GetMapping("/files")
    public String getListFiles(Model model) {
        List<FileInfo> fileInfos = fileStorage.loadFiles().map(
                path ->  {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                            "downloadFile", path.getFileName().toString()).build().toString();
                    return new FileInfo(filename, url);
                }
        )
                .collect(Collectors.toList());

        model.addAttribute("files", fileInfos);
        return "listfiles";
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = fileStorage.loadFile(filename,"xml");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename().toString() + "\"")
                .body(file);
    }

//    private static final String DIRECTORY = "d:\\IdeaProject\\szvk_spring\\mail\\requests\\";
//    private static final String  DEFAULT_FILE_NAME ="";
//
//    @Autowired
//    private ServletContext servletContext;
//
//    // http://localhost:8080/download1?fileName=abc.zip
//    // Using ResponseEntity<InputStreamResource>
//    @RequestMapping("/download")
//    public ResponseEntity<InputStreamResource> downloadFile1(
//            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
//
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
////        System.out.println("fileName: " + fileName);
////        System.out.println("mediaType: " + mediaType);
//        fileName = ProcessingController.getNameFile();
//        File file = new File(fileName);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        return ResponseEntity.ok()
//                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
//                .contentLength(file.length()) //
//                .body(resource);
//    }
}
