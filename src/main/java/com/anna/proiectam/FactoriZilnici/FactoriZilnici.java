package com.anna.proiectam.FactoriZilnici;

import com.anna.proiectam.Utilizator.Utilizator;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="FactoriZilnici")
public class FactoriZilnici
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IDFactoriZilnici", nullable=false)
    private Long idFactoriZilnici;

    @ManyToOne
    @JoinColumn(name="IDUtilizator", nullable=false)
    private Utilizator utilizator1;

    @Column(name="Data", nullable=false)
    private LocalDate data;

    @Column(name="OreSomn", nullable=false)
    private Integer oreSomn;

    @Column(name="MinuteSomn", nullable=false)
    private Integer minuteSomn;

    @Column(name="Stres", nullable=false)
    private Integer stres;

    @Column(name="ActivitateFizica", nullable=false)
    private Integer activitateFizica;

    @Column(name="ActivitateSociala", nullable=false)
    private Integer activitateSociala;

    @Column(name="Mancare", nullable=false)
    private Integer mancare;

    public FactoriZilnici(){}

    public Long getId()
    {
        return idFactoriZilnici;
    }
    public void setId(Long idFactoriZilnici)
    {
        this.idFactoriZilnici = idFactoriZilnici;
    }
    public Utilizator getUtilizator1()
    {
        return utilizator1;
    }
    public void setUtilizator1(Utilizator utilizator1)
    {
        this.utilizator1 = utilizator1;
    }
    public LocalDate getData()
    {
        return data;
    }
    public void setData(LocalDate data)
    {
        this.data = data;
    }
    public Integer getOreSomn()
    {
        return oreSomn;
    }
    public void setOreSomn(Integer oreSomn)
    {
        this.oreSomn = oreSomn;
    }
    public Integer getMinuteSomn()
    {
        return minuteSomn;
    }
    public void setMinuteSomn(Integer minuteSomn)
    {
        this.minuteSomn = minuteSomn;
    }
    public Integer getStres()
    {
        return stres;
    }
    public void setStres(Integer stres)
    {
        this.stres = stres;
    }
    public Integer getActivitateFizica()
    {
        return activitateFizica;
    }
    public void setActivitateFizica(Integer activitateFizica)
    {
        this.activitateFizica = activitateFizica;
    }
    public Integer getActivitateSociala()
    {
        return activitateSociala;
    }

    public void setActivitateSociala(Integer activitateSociala)
    {
        this.activitateSociala = activitateSociala;
    }
    public Integer getMancare()
    {
        return mancare;
    }
    public void setMancare(Integer mancare)
    {
        this.mancare = mancare;
    }

}
