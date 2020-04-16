package ru.pfr.szvk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class Contoller {
    @GetMapping("/")
    public String home( Model model) {
        model.addAttribute("title", "ОПФР по г. Севастополю  СЗВ-К");
        return "home";
    }

    @GetMapping("/about")
    public String about( Model model) {
        model.addAttribute("title", "О ПРОЕКТЕ СЗВ-К");
        return "about";
    }

}
