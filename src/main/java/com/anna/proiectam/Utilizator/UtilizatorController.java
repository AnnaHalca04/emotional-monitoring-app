package com.anna.proiectam.Utilizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtilizatorController {

    private final UtilizatorService utilizatorService;

    @Autowired
    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/inregistrare")
    public String afiseazaInregistrare(Model model) {
        model.addAttribute("utilizator", new Utilizator());
        return "inregistrare";
    }

    @PostMapping("/inregistrare")
    public String proceseazaInregistrare(@ModelAttribute Utilizator utilizator, Model model) {
        try {
            utilizatorService.inregistreazaUtilizator(utilizator);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("eroare", e.getMessage());
            return "inregistrare";
        }
    }


}