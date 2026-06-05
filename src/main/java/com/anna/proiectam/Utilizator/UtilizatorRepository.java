package com.anna.proiectam.Utilizator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator,Long>
{
    Optional<Utilizator> findByEmail(String email);
}
