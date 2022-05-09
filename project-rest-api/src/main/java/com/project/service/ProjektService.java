package com.project.service;
import java.util.Optional;

import com.project.repository.ZadanieRepository;
import com.project.rest.model.Zadanie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.rest.model.Projekt;
import com.project.repository.ProjektRepository;

import javax.transaction.Transactional;

@Service
public class ProjektService implements ProjektServiceInterface {
    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;

    @Autowired
    public ProjektService(ProjektRepository projektRepository, ZadanieRepository zadanieRepo) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepo;
    }

    @Override
    public Optional<Projekt> getProjekt(Integer projektId){

        return projektRepository.findById(projektId);
    }

    @Override
    public Projekt setProjekt(Projekt projekt) {

        return projektRepository.save(projekt);
    }
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {
        for(Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)){
            zadanieRepository.delete(zadanie);
        }
        projektRepository.deleteById(projektId);

    }
    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektRepository.findAll(pageable);

    }

    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        return projektRepository.findByNazwaContaining(nazwa, pageable);

    }

}
