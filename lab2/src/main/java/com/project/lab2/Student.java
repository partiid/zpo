package com.project.lab2;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="student")
public class Student {
    public Student() {
    }
    public Student(String imie, String nazwisko, String nrIndeksu, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
    }
    public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
        this.email = email;
        this.stacjonarny = stacjonarny;
    }

    @ManyToMany(mappedBy = "studenci")
    private Set<Project> projekty;

    public Set<Project> getProjekty() {
        return projekty;
    }

    public void setProjekty(Set<Project> projekty) {
        this.projekty = projekty;
    }

    @Id
    @GeneratedValue
    @Column(name="student_id")
    private Integer student_id;

    @Column(name="imie")
    private String imie;
    @Column(name="nazwisko")
    private String nazwisko;
    @Column(name="email")
    private String email;

    @Column(name="nr_indeksu")
    private String nrIndeksu;
    @Column(name="stacjonarny")
    private Boolean stacjonarny;


    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNrIndeksu() {
        return nrIndeksu;
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }

    public Boolean getStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(Boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }






}
