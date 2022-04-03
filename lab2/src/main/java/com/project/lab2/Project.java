package com.project.lab2;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
@Entity
@Table(name="projekt") //potrzebne tylko jeżeli nazwa tabeli w bazie danych ma być inna od nazwy klasy
public class Project {

    public Project(){

    }
    public Project(Integer projekt_id, String nazwa, String opis, LocalDateTime dataCzasUtworzenia, LocalDate dataOddania) {
        this.projektId = projekt_id;
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataCzasUtworzenia;
        this.dataOddania = dataOddania;

    }

    public Project(String nazwa, String opis, LocalDate dataOddania ){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataOddania = dataOddania;

    }
    @OneToMany(mappedBy = "projekt")
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "projekt_student",
            joinColumns = {@JoinColumn(name="projekt_id")},
            inverseJoinColumns = {@JoinColumn(name="student_id")})
    private Set<Student> studenci;



    @Id
    @GeneratedValue
    @Column(name="projekt_id") //tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
    private Integer projektId;@Column(nullable = false, length = 50)
    private String nazwa;
    @Column(length = 1000)
    private String opis;
    @CreationTimestamp
    @Column(name = "dataczas_utworzenia", nullable = false, updatable=false)
    private LocalDateTime dataCzasUtworzenia;
    @Column(name = "data_oddania")
    private LocalDate dataOddania;
    /* TODO Wygeneruj dla powyższych zmiennych akcesory (Source -> Generate Getters and Setters).
     * Dodaj również bezparametrowy konstruktor oraz drugi konstruktor uwzględniający wszystkie powyższe
     * zmienne, a także trzeci pomijający pola projektId oraz dataCzasUtworzenia.
     */

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public LocalDate getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(LocalDate dataOddania) {
        this.dataOddania = dataOddania;
    }
}