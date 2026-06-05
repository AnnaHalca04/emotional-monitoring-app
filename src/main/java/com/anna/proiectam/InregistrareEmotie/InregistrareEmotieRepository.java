package com.anna.proiectam.InregistrareEmotie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InregistrareEmotieRepository extends JpaRepository<InregistrareEmotie,Long> {
    @Query("SELECT COUNT(ie) > 0 FROM InregistrareEmotie ie " +
            "JOIN ie.inregistrare i " +
            "WHERE i.utilizator.idUtilizator = :idUtilizator " +
            "AND ie.tipEmotie.numeEmotie = :numeEmotie " +
            "AND CAST(i.dataOra AS LocalDate) = :data")
    boolean existsEmotieInZi(@Param("idUtilizator") Long idUtilizator,
                             @Param("numeEmotie") String numeEmotie,
                             @Param("data") LocalDate data);

    @Query("SELECT ie.tipEmotie.numeEmotie FROM InregistrareEmotie ie " +
            "JOIN ie.inregistrare i " +
            "WHERE i.utilizator.idUtilizator = :idUtilizator " +
            "ORDER BY i.dataOra DESC LIMIT 3")
    List<String> findUltimeleEmotii(@Param("idUtilizator") Long idUtilizator);

    @Query("SELECT CAST(i.dataOra AS LocalDate) as zi, AVG(CAST(ie.intensitate AS double)) as medieIntensitate " +
            "FROM InregistrareEmotie ie " +
            "JOIN ie.inregistrare i " +
            "WHERE i.utilizator.idUtilizator = :idUtilizator " +
            "AND i.dataOra >= :de " +
            "GROUP BY CAST(i.dataOra AS LocalDate) " +
            "ORDER BY CAST(i.dataOra AS LocalDate) ASC")
    List<Object[]> findEvolutieStare(@Param("idUtilizator") Long idUtilizator,
                                     @Param("de") LocalDateTime de);
}
