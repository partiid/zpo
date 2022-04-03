package com.project.projektDAO;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import com.project.datasource.JPAUtil;
import com.project.lab2.Projekt;

public class ProjektDAO implements ProjektDAOInterface {

    @Override
    public Projekt getProjekt(Integer projektId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Projekt projekt = entityManager.find(Projekt.class, projektId);
        entityManager.close();
        return projekt;
    }
    @Override
    public void setProjekt(Projekt projekt) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        String query = "INSERT INTO projekt (nazwa, opis, data_oddania, dataczas_utworzenia) VALUES (?, ?, ?, ?)";

        entityManager.createNativeQuery(query).setParameter(1, projekt.getNazwa())
                .setParameter(2, projekt.getOpis())
                .setParameter(3, projekt.getDataOddania())
                .setParameter(4, new LocalDateTime[]{LocalDateTime.now()})
                .executeUpdate();


        entityManager.close();

    }
    @Override
    public void deleteProjekt(Integer projektId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Projekt projekt = entityManager.find(Projekt.class, projektId);
        entityManager.remove(projekt);
    }
    @Override
    public List<Projekt> getProjekty(Integer offset, Integer limit) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        List<Projekt> projekty = entityManager.createQuery("SELECT p FROM Projekt p", Projekt.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        entityManager.close();
        if((long)projekty.size() == 0) {
            return null;
        } else {
            return projekty;
        }

    }
    @Override
    public List<Projekt> searchByNazwa(String search4, Integer offset, Integer limit) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        List<Projekt> projekty = entityManager.createQuery("SELECT p FROM Projekt p WHERE p.nazwa LIKE :search4", Projekt.class)
                .setParameter("search4", "%" + search4 + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        entityManager.close();
        if((long)projekty.size() == 0) {
            return null;
        } else {
            return projekty;
        }

    }
}
