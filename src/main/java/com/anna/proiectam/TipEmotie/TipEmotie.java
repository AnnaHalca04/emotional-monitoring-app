package com.anna.proiectam.TipEmotie;

import jakarta.persistence.*;

@Entity
@Table(name="TipEmotie")
public class TipEmotie {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="IDTipEmotie", nullable=false)
    private Long idTipEmotie;

    @Column(name="NumeEmotie", length=50, nullable=false)
    private String numeEmotie;

    private Long getIdTipEmotie()
    {
        return idTipEmotie;
    }
    private void setIdTipEmotie(Long idTipEmotie)
    {
        this.idTipEmotie = idTipEmotie;
    }
    private String getNumeEmotie()
    {
        return numeEmotie;
    }
    private void setNumeEmotie(String numeEmotie)
    {
        this.numeEmotie = numeEmotie;
    }
    public TipEmotie(){}

}
