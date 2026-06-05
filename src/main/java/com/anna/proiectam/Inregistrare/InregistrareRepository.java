package com.anna.proiectam.Inregistrare;

import com.anna.proiectam.Utilizator.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InregistrareRepository extends JpaRepository<Inregistrare, Long> {
    @Query("SELECT COUNT(i) > 0 FROM Inregistrare i " +
            "WHERE i.utilizator = :utilizator " +
            "AND CAST(i.dataOra AS LocalDate) = :data")
    boolean existsByUtilizatorAndData(@Param("utilizator") Utilizator utilizator,
                                      @Param("data") LocalDate data);
}
