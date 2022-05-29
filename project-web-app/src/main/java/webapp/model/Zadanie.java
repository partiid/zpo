package webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zadanie {


    public Integer zadanieId;


    private Projekt projekt;


    public String nazwa;



    public Integer kolejnosc;


    private String opis;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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
