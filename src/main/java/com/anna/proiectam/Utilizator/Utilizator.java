package com.anna.proiectam.Utilizator;

import com.anna.proiectam.FactoriZilnici.FactoriZilnici;
import com.anna.proiectam.Inregistrare.Inregistrare;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Utilizator")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IDUtilizator", nullable=false)
    private Long idUtilizator;

    @Column(name="Nume", nullable=false, length=50)
    private String nume;

    @Column(name="Prenume", nullable=false, length=50)
    private String prenume;

    @Column(name="Email", nullable=false, length=100)
    private String email;

    @Column(name="Parola", nullable=false, length=255)
    private String parola;

    @Column(name="NrTelefon", length=15)
    private String nrTelefon;

    @Column(name="DataInregistrarii")
    private LocalDateTime dataInregistrarii;

    @Column(name="Streak")
    private Integer streak;

    @OneToMany(mappedBy="utilizator")
    private List<Inregistrare> inregistrari;

    @OneToMany(mappedBy="utilizator1")
    private List<FactoriZilnici>  factoriZilnici;

    public Long getIdUtilizator()
    {
        return idUtilizator;
    }
    public void setIdUtilizator(Long idUtilizator)
    {
        this.idUtilizator = idUtilizator;
    }
    public String getNume()
    {
        return nume;
    }
    public void setNume(String nume)
    {
        this.nume = nume;
    }
    public String getPrenume()
    {
        return prenume;
    }
    public void setPrenume(String prenume)
    {
        this.prenume=prenume;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public String getParola()
    {
        return parola;
    }
    public void setParola(String parola)
    {
        this.parola=parola;
    }
    public String getNrTelefon()
    {
        return nrTelefon;
    }
    public void setNrTelefon(String nrTelefon)
    {
        this.nrTelefon=nrTelefon;
    }
    public LocalDateTime  getDataInregistrarii()
    {
        return dataInregistrarii;
    }
    public void setDataInregistrarii(LocalDateTime dataInregistrarii)
    {
        this.dataInregistrarii=dataInregistrarii;
    }
    public Integer getStreak()
    {
        return streak;
    }
    public void setStreak(Integer streak)
    {
        this.streak=streak;
    }
    public List<Inregistrare>  getInregistrari()
    {
        return inregistrari;
    }
    public void setInregistrari(List<Inregistrare> inregistrari)
    {
        this.inregistrari=inregistrari;
    }
    public List<FactoriZilnici> getFactoriZilnici()
    {
        return factoriZilnici;
    }
    public void setFactoriZilnici(List<FactoriZilnici> factoriZilnici)
    {
        this.factoriZilnici=factoriZilnici;
    }
    public Utilizator(){}

}







