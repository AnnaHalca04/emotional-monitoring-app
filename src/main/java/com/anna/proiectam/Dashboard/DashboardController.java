package com.anna.proiectam.Dashboard;

import com.anna.proiectam.FactoriZilnici.FactoriZilnici;
import com.anna.proiectam.FactoriZilnici.FactoriZilniciRepository;
import com.anna.proiectam.FactoriZilnici.FactoriZilniciService;
import com.anna.proiectam.InregistrareEmotie.InregistrareEmotieRepository;
import com.anna.proiectam.Utilizator.Utilizator;
import com.anna.proiectam.Utilizator.UtilizatorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class DashboardController {

    private final FactoriZilniciService factoriZilniciService;
    private final InregistrareEmotieRepository inregistrareEmotieRepository;
    private final MLService mlService;
    private final AvertizareService avertizareService;
    private final FactoriZilniciRepository factoriZilniciRepository;
    private final IntrebareReflectivaService intrebareReflectivaService;

    @Autowired
    public DashboardController(FactoriZilniciService factoriZilniciService,
                               InregistrareEmotieRepository inregistrareEmotieRepository,
                               AvertizareService avertizareService,
                               MLService mlService, FactoriZilniciRepository factoriZilniciRepository,
                               IntrebareReflectivaService intrebareReflectivaService) {
        this.factoriZilniciService = factoriZilniciService;
        this.inregistrareEmotieRepository = inregistrareEmotieRepository;
        this.mlService = mlService;
        this.avertizareService = avertizareService;
        this.factoriZilniciRepository = factoriZilniciRepository;
        this.intrebareReflectivaService = intrebareReflectivaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UtilizatorDetails utilizatorDetails,
                            Model model) {

        Utilizator utilizator = utilizatorDetails.getUtilizator();

        // ── Salut ──
        model.addAttribute("nume", utilizator.getPrenume());
        model.addAttribute("dataAzi", LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", new Locale("ro"))));

        // ── Streak ──
        model.addAttribute("streak", utilizator.getStreak());

        model.addAttribute("intrebare", intrebareReflectivaService.getIntrebareZilnica());


        // ── Predictie ML ──
        FactoriZilnici factoriAzi = factoriZilniciRepository
                .findByUtilizator1AndData(utilizator, LocalDate.now())
                .orElse(null);

        if (factoriAzi != null) {
            MLService.PredictieML predictie = mlService.getPredictie(factoriAzi);
            if (predictie != null) {
                model.addAttribute("scorML", predictie.scor());
                model.addAttribute("tipZiML", predictie.tipZi());
            }
        }

        // ── Act 2 completat azi? ──
        model.addAttribute("aCompletatAzi",
                factoriZilniciService.aCompletatAzi(utilizator));

        // ── Ultima emoție ──
        List<String> ultimele = inregistrareEmotieRepository
                .findUltimeleEmotii(utilizator.getIdUtilizator());
        if (!ultimele.isEmpty()) {
            model.addAttribute("ultimaEmotie", String.join(", ", ultimele));
        }

        // ── Avertizări ──
        List<AvertizareService.Avertizare> avertizari =
                avertizareService.genereazaAvertizari(utilizator);
        model.addAttribute("avertizari", avertizari);

        // ── Grafic 7 zile ──
        List<Object[]> evolutie7 = inregistrareEmotieRepository
                .findEvolutieStare(utilizator.getIdUtilizator(),
                        LocalDateTime.now().minusDays(7));

        List<String> zile7 = new ArrayList<>();
        List<Double> scoruri7 = new ArrayList<>();
        for (Object[] row : evolutie7) {
            zile7.add(row[0].toString().substring(5)); // MM-DD
            scoruri7.add(row[1] != null ? ((Number) row[1]).doubleValue() : 0.0);
        }

        model.addAttribute("zile7", zile7);
        model.addAttribute("scoruri7", scoruri7);

        // ── Grafic 30 zile ──
        List<Object[]> evolutie30 = inregistrareEmotieRepository
                .findEvolutieStare(utilizator.getIdUtilizator(),
                        LocalDateTime.now().minusDays(30));

        List<String> zile30 = new ArrayList<>();
        List<Double> scoruri30 = new ArrayList<>();
        for (Object[] row : evolutie30) {
            zile30.add(row[0].toString().substring(5));
            scoruri30.add(row[1] != null ? ((Number) row[1]).doubleValue() : 0.0);
        }

        model.addAttribute("zile30", zile30);
        model.addAttribute("scoruri30", scoruri30);
        model.addAttribute("areDate", !zile7.isEmpty() || !zile30.isEmpty());
        return "dashboard";
    }
}