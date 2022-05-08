package com.project.lab2;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="zadanie")
public class Zadanie {

    @Id
    @GeneratedValue
    @Column(name="zadanie_id")
    public Integer zadanieId;

    @ManyToOne
    @JoinColumn(name="projekt_id")
    private Projekt projekt;

    @Column(name="nazwa", nullable=false)
    public String nazwa;


    @Column(name="kolejnosc")
    public Integer kolejnosc;

    @Column(name="opis")
    private String opis;

    @CreationTimestamp
    @Column(name="dataczas_dodania", nullable=false, updatable=false)
    public LocalDateTime dataCzasDodania;



    public Zadanie(){

    }
    public Zadanie(String nazwa, String opis, Integer kolejnosc){
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;

    }


    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataCzasDodania() {
        return dataCzasDodania;
    }

    public void setDataCzasDodania(LocalDateTime dataCzasDodania) {
        this.dataCzasDodania = dataCzasDodania;
    }


}
