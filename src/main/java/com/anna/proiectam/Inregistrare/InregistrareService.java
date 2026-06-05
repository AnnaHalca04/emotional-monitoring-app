package com.anna.proiectam.Inregistrare;

import com.anna.proiectam.InregistrareEmotie.InregistrareEmotie;
import com.anna.proiectam.InregistrareEmotie.InregistrareEmotieRepository;
import com.anna.proiectam.TipEmotie.TipEmotie;
import com.anna.proiectam.TipEmotie.TipEmotieRepository;
import com.anna.proiectam.Utilizator.Utilizator;
import com.anna.proiectam.Utilizator.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class InregistrareService {

    private final InregistrareRepository inregistrareRepository;
    private final InregistrareEmotieRepository inregistrareEmotieRepository;
    private final TipEmotieRepository tipEmotieRepository;
    private final UtilizatorRepository utilizatorRepository;

    @Autowired
    public InregistrareService(InregistrareRepository inregistrareRepository,
                               InregistrareEmotieRepository inregistrareEmotieRepository,
                               TipEmotieRepository tipEmotieRepository, UtilizatorRepository utilizatorRepository) {
        this.inregistrareRepository = inregistrareRepository;
        this.inregistrareEmotieRepository = inregistrareEmotieRepository;
        this.tipEmotieRepository = tipEmotieRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    public void salveazaInregistrare(Utilizator utilizator,
                                     String emotiiStr,
                                     String intensitatiStr,
                                     String senzatiiStr,
                                     String locatii) {
        // 1. Creează înregistrarea
        Inregistrare inregistrare = new Inregistrare();
        inregistrare.setUtilizator(utilizator);
        inregistrare.setDataOra(LocalDateTime.now());
        inregistrareRepository.save(inregistrare);

        // 2. Parsează datele
        String[] emotii = emotiiStr.split(",");
        String[] intensitati = intensitatiStr.split(";");
        String[] senzatii = senzatiiStr.split(";");

        // 3. Salvează fiecare emoție
        for (int i = 0; i < emotii.length; i++) {
            String numeEmotie = emotii[i].trim();

            TipEmotie tipEmotie = tipEmotieRepository.findByNumeEmotie(numeEmotie);
            if (tipEmotie == null) continue;

            InregistrareEmotie ie = new InregistrareEmotie();
            ie.setInregistrare(inregistrare);
            ie.setTipEmotie(tipEmotie);

            // intensitate: "Bucurie:7" → 7
            if (i < intensitati.length) {
                String[] parts = intensitati[i].split(":");
                if (parts.length > 1) {
                    try { ie.setIntensitate(Integer.parseInt(parts[1])); }
                    catch (NumberFormatException ignored) {}
                }
            }

            // senzatie: "Bucurie:apăsare în piept" → "apăsare în piept"
            if (i < senzatii.length) {
                String[] parts = senzatii[i].split(":", 2);
                if (parts.length > 1) ie.setSenzatie(parts[1]);
            }

            // locatii — aceleași pentru toate emoțiile
            ie.setLocatie(locatii);

            inregistrareEmotieRepository.save(ie);
        }
        actualizeazaStreak(utilizator);

    }
    private void actualizeazaStreak(Utilizator utilizator) {
        LocalDate azi = LocalDate.now();
        LocalDate ieri = azi.minusDays(1);

        boolean aInregistratIeri = inregistrareRepository
                .existsByUtilizatorAndData(utilizator, ieri);
        boolean aInregistratAzi = inregistrareRepository
                .existsByUtilizatorAndData(utilizator, azi);

        if (aInregistratAzi) return;

        if (aInregistratIeri) {
            Integer streakCurent = utilizator.getStreak();
            utilizator.setStreak(streakCurent == null ? 1 : streakCurent + 1);
        } else {
            utilizator.setStreak(1);
        }

        utilizatorRepository.save(utilizator);
    }

}