package webapp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Projekt {


    private Integer projektId;


    private String nazwa;


    private String opis;

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasUtworzenia;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOddania;



    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasModyfikacji;


    private List<Zadanie> zadania;


    private Set<Student> studenci;


    public Projekt() {

    }

    public Projekt(Integer projekt_id, String nazwa, String opis, LocalDateTime dataCzasUtworzenia, LocalDate dataOddania) {
        this.projektId = projekt_id;
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataCzasUtworzenia;
        this.dataOddania = dataOddania;

    }

    public Projekt(String nazwa, String opis, LocalDate dataOddania) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataOddania = dataOddania;

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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataCzasUtworzenia() {
        return dataCzasUtworzenia;
    }

    public void setDataCzasUtworzenia(LocalDateTime dataCzasUtworzenia) {
        this.dataCzasUtworzenia = dataCzasUtworzenia;
    }
    public LocalDateTime getDataCzasModyfikacji() {
        return dataCzasModyfikacji;
    }
    public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji){
        this.dataCzasModyfikacji = dataCzasModyfikacji;
    }

    public LocalDate getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(LocalDate dataOddania) {
        this.dataOddania = dataOddania;
    }

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }

    public Set<Student> getStudenci() {
        return studenci;
    }

    public void setStudenci(Set<Student> studenci) {
        this.studenci = studenci;
    }


}