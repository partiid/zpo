package com.project.app;

import com.project.lab2.Projekt;
import com.project.projektDAO.ProjektDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class ProjectClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(ProjectClientApplication.class);

    public static void main(String[] args) {
        ProjektDAO projektDAO = new ProjektDAO();


        try {
            Projekt projekt = new Projekt("Projekt testowy", "Opis testowy", LocalDate.of(2020, 6, 22));
                    projektDAO.setProjekt(projekt);
            logger.info("Id utworzonego projektu: {}", projekt.getProjektId());
//System.out.println("Id utworzonego projektu: " + projekt.getProjektId());
        } catch (RuntimeException e) {
            logger.error("Błąd podczas dodawania projektu!", e);
        }

    }
}
