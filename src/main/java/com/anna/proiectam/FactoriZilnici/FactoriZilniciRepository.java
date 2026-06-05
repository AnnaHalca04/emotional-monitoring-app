package com.anna.proiectam.FactoriZilnici;

import com.anna.proiectam.Utilizator.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FactoriZilniciRepository extends JpaRepository<FactoriZilnici, Long> {
    Optional<FactoriZilnici> findByUtilizator1AndData(Utilizator utilizator1, LocalDate data);}