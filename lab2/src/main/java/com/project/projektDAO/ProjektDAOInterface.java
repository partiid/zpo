package com.project.projektDAO;
import com.project.lab2.Projekt;

import java.util.List;

public interface ProjektDAOInterface {
    Projekt getProjekt(Integer projektId);
    void setProjekt(Projekt projekt);
    void deleteProjekt(Integer projektId);
    List<Projekt> getProjekty(Integer offset, Integer limit);
    List<Projekt> searchByNazwa(String search4, Integer offset, Integer limit);

}
