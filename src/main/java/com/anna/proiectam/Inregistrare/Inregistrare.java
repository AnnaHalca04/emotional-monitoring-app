package com.anna.proiectam.Inregistrare;

import com.anna.proiectam.Utilizator.Utilizator;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Inregistrare")
public class Inregistrare
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="IDInregistrare", nullable=false)
    private Long idInregistrare;

    @Column(name="DataOra")
    private LocalDateTime dataOra;

    @ManyToOne
    @JoinColumn(name="IDUtilizator", nullable=false)
    private Utilizator utilizator;

    public Inregistrare(){}

    public Long getIdInregistrare()
    {
        return idInregistrare;
    }
    public void setIdInregistrare(Long idInregistrare)
    {
        this.idInregistrare = idInregistrare;
    }
    public LocalDateTime getDataOra()
    {
        return dataOra;
    }
    public void setDataOra(LocalDateTime dataOra)
    {
        this.dataOra = dataOra;
    }
    public Utilizator getUtilizator()
    {
        return utilizator;
    }
    public void setUtilizator(Utilizator utilizator)
    {
        this.utilizator = utilizator;
    }
}
