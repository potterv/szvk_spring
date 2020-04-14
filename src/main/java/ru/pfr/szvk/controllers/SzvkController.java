package ru.pfr.szvk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pfr.szvk.models.Roles;
import ru.pfr.szvk.repo.RolesRepository;

import javax.management.relation.RoleStatus;

@Controller
public class SzvkController {
    @GetMapping("/uploadfiles")
    public String uploadFiles(Model model){
        return "uploadfiles";
    }

    @GetMapping("/index")
    public String index( Model model) {
        Iterable<Roles> roles = rolesRepository.findAll();
        model.addAttribute("roles",roles);
        return "index";
    }
    @Autowired
    private RolesRepository rolesRepository;
}
