package com.anna.proiectam.InregistrareEmotie;

import com.anna.proiectam.Inregistrare.Inregistrare;
import com.anna.proiectam.TipEmotie.TipEmotie;
import jakarta.persistence.*;

@Entity
@Table(name="InregistrareEmotie")
public class InregistrareEmotie
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="IDInregistrareEmotie", nullable=false)
    private Integer idInregistrareEmotie;

    @ManyToOne
    @JoinColumn(name="IDInregistrare", nullable=false)
    private Inregistrare inregistrare;

    @ManyToOne
    @JoinColumn(name="IDTipEmotie", nullable=false)
    private TipEmotie tipEmotie;

    @Column(name="Intensitate", nullable=false)
    private Integer intensitate;

    @Column(name="Senzatie", length=255)
    private String senzatie;

    @Column(name="Locatie", length=255)
    private String locatie;

    public InregistrareEmotie(){}

    public Integer getIdInregistrareEmotie()
    {
        return idInregistrareEmotie;
    }
    public void setIdInregistrareEmotie(Integer idInregistrareEmotie)
    {
        this.idInregistrareEmotie = idInregistrareEmotie;
    }
    public Inregistrare getInregistrare()
    {
        return inregistrare;
    }
    public void setInregistrare(Inregistrare inregistrare)
    {
        this.inregistrare = inregistrare;
    }
    public TipEmotie getTipEmotie()
    {
        return tipEmotie;
    }
    public void setTipEmotie(TipEmotie tipEmotie)
    {
        this.tipEmotie = tipEmotie;
    }
    public Integer getIntensitate()
    {
        return intensitate;
    }
    public void setIntensitate(Integer intensitate)
    {
        this.intensitate = intensitate;
    }
    public String getSenzatie()
    {
        return senzatie;
    }
    public void setSenzatie(String senzatie)
    {
        this.senzatie = senzatie;
    }
    public String getLocatie()
    {
        return locatie;
    }
    public void setLocatie(String locatie)
    {
        this.locatie = locatie;
    }
}
