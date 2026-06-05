package com.anna.proiectam.Inregistrare;

import com.anna.proiectam.Utilizator.Utilizator;
import com.anna.proiectam.Utilizator.UtilizatorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InregistrareController {

    private final InregistrareService inregistrareService;

    @Autowired
    public InregistrareController(InregistrareService inregistrareService) {
        this.inregistrareService = inregistrareService;
    }

    @GetMapping("/emotie/adauga")
    public String afiseazaFormular() {
        return "inregistrare-emotie";
    }

    @PostMapping("/emotie/adauga")
    public String salveazaEmotie(
            @AuthenticationPrincipal UtilizatorDetails utilizatorDetails,
            @RequestParam String emotiiSelectate,
            @RequestParam String intensitati,
            @RequestParam String senzatii,
            @RequestParam String locatii) {

        Utilizator utilizator = utilizatorDetails.getUtilizator();

        inregistrareService.salveazaInregistrare(
                utilizator,
                emotiiSelectate,
                intensitati,
                senzatii,
                locatii
        );

        return "redirect:/dashboard";
    }
}