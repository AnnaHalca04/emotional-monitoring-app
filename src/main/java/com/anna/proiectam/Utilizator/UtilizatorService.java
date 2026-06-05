package com.anna.proiectam.Utilizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UtilizatorService implements UserDetailsService {

    private final UtilizatorRepository utilizatorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UtilizatorService(UtilizatorRepository utilizatorRepository,
                             BCryptPasswordEncoder passwordEncoder) {
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilizator utilizator = utilizatorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost găsit!"));
        return new UtilizatorDetails(utilizator);
    }

    public void inregistreazaUtilizator(Utilizator utilizator) {
        if (utilizatorRepository.findByEmail(utilizator.getEmail()).isPresent()) {
            throw new RuntimeException("Ești deja înregistrat în aplicație!");
        }
        String parolaCriptata = passwordEncoder.encode(utilizator.getParola());
        utilizator.setDataInregistrarii(LocalDateTime.now());
        utilizator.setParola(parolaCriptata);
        utilizator.setStreak(0);
        utilizatorRepository.save(utilizator);
    }
}