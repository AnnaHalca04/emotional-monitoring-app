package com.anna.proiectam.FactoriZilnici;

import com.anna.proiectam.Utilizator.Utilizator;
import com.anna.proiectam.Utilizator.UtilizatorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FactoriZilniciController {

    private final FactoriZilniciService factoriZilniciService;

    @Autowired
    public FactoriZilniciController(FactoriZilniciService factoriZilniciService) {
        this.factoriZilniciService = factoriZilniciService;
    }

    @GetMapping("/factori/adauga")
    public String afiseazaFormular(
            @AuthenticationPrincipal UtilizatorDetails utilizatorDetails,
            Model model) {

        Utilizator utilizator = utilizatorDetails.getUtilizator();

        if (factoriZilniciService.aCompletatAzi(utilizator)) {
            model.addAttribute("eroare", "Ai completat deja factorii zilnici pentru astăzi!");
            return "factori-zilnici";
        }

        return "factori-zilnici";
    }

    @PostMapping("/factori/adauga")
    public String salveazaFactori(
            @AuthenticationPrincipal UtilizatorDetails utilizatorDetails,
            @RequestParam int oreSomn,
            @RequestParam int minuteSomn,
            @RequestParam int stres,
            @RequestParam int activitateFizica,
            @RequestParam int activitateSociala,
            @RequestParam int mancare,
            Model model) {

        Utilizator utilizator = utilizatorDetails.getUtilizator();

        try {
            factoriZilniciService.salveazaFactori(
                    utilizator, oreSomn, minuteSomn,
                    stres, activitateFizica, activitateSociala, mancare);
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("eroare", e.getMessage());
            return "factori-zilnici";
        }
    }
}