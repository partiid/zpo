package com.project.repository;

import com.project.rest.model.Projekt;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProjektRepository extends JpaRepository<Projekt, Integer> {


    Page<Projekt> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);

    List<Projekt> findByNazwaContainingIgnoreCase(String nazwa);

    Page<Projekt> findByNazwaContaining(String nazwa, Pageable pageable);
}
