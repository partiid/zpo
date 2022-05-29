package webapp.service;

import webapp.model.Zadanie;
import webapp.model.Projekt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ZadanieServiceInterface {
    Optional<Projekt> getZadanie(Integer zadanieId);
    Zadanie setZadanie(Zadanie zadanie);
    void deleteZadanie(Integer zadanieId);
    Page<Projekt> getZadania(Pageable pageable);
    Page<Projekt> searchByNazwa(String nazwa, Pageable pageable);
}
