package com.project.lab2;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="zadanie")
public class Task {
    public Task(){

    }
    public Task(String nazwa, String opis, Integer kolejnosc){
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;

    }
    @Id
    @GeneratedValue
    @Column(name="zadanie_id")
    private Integer zadanieId;

    @Column(name="projekt_id")
    private Integer projektId;

    public void setProjekt(Project projekt) {
        this.projekt = projekt;
    }

    @Column(name="nazwa", nullable=false)
    private String nazwa;
    @Column(name="kolejnosc")
    private Integer kolejnosc;

    @Column(name="opis")
    private String opis;

    @CreationTimestamp
    @Column(name="dataczas_dodania", nullable=false, updatable=false)
    private LocalDateTime dataCzasDodania;

    @ManyToOne
    @JoinColumn(name="projekt_id")
    private Project projekt;

    public Project getProjekt() {
        return projekt;
    }

    public void setProject(Project project) {
        this.projekt = projekt;
    }

    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public Integer getProjektId() {
        return projektId;
    }

    public void setProjektId(Integer projektId) {
        this.projektId = projektId;
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
