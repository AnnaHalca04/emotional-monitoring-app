package com.anna.proiectam.FactoriZilnici;

import com.anna.proiectam.Utilizator.Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FactoriZilniciService {

    private final FactoriZilniciRepository factoriZilniciRepository;

    @Autowired
    public FactoriZilniciService(FactoriZilniciRepository factoriZilniciRepository) {
        this.factoriZilniciRepository = factoriZilniciRepository;
    }

    public boolean aCompletatAzi(Utilizator utilizator) {
        return factoriZilniciRepository
                .findByUtilizator1AndData(utilizator, LocalDate.now())
                .isPresent();
    }

    public void salveazaFactori(Utilizator utilizator,
                                int oreSomn,
                                int minuteSomn,
                                int stres,
                                int activitateFizica,
                                int activitateSociala,
                                int mancare) {

        if (aCompletatAzi(utilizator)) {
            throw new RuntimeException("Ai completat deja factorii zilnici pentru astăzi!");
        }

        FactoriZilnici factori = new FactoriZilnici();
        factori.setUtilizator1(utilizator);
        factori.setData(LocalDate.now());
        factori.setOreSomn(oreSomn);
        factori.setMinuteSomn(minuteSomn);
        factori.setStres(stres);
        factori.setActivitateFizica(activitateFizica);
        factori.setActivitateSociala(activitateSociala);
        factori.setMancare(mancare);

        factoriZilniciRepository.save(factori);
    }
}