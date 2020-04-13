package ru.pfr.szvk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SzvkController {
    @GetMapping("/uploadfiles")
    public String uploadFiles(Model model){
        return "uploadfiles";
    }
}
