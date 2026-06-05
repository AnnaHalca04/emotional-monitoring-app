package com.anna.proiectam.TipEmotie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipEmotieRepository extends JpaRepository<TipEmotie,Long> {
    TipEmotie findByNumeEmotie(String numeEmotie);
}
