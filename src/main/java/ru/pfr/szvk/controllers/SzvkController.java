package ru.pfr.szvk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/roles")
    public String setreles( Model model) {

        return "setroles";
    }

    @PostMapping("/role/add")
    public String releAdd(@RequestParam String nameRole, @RequestParam String description, Model model) {
        Roles roles =new Roles(nameRole,description);
        rolesRepository.save(roles);
        return "redirect:/";
    }


}
