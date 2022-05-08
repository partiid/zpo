package com.project.projektDAO;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import com.project.datasource.JPAUtil;
import com.project.lab2.Projekt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjektDAO implements ProjektDAOInterface {
    public static final Logger logger = LoggerFactory.getLogger(ProjektDAO.class);


    @Override
    public Projekt getProjekt(Integer projektId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            return entityManager.find(Projekt.class, projektId);

        }catch (NullPointerException e){
            logger.error("Could not get any project with matching ID NullPointerException: " + e.getMessage());
            return null;

        }finally {
            entityManager.close();
        }


    }

    @Override
    @Transactional
    public void setProjekt(Projekt projekt) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(projekt);
            transaction.commit();

        }catch(Exception e){
            transaction.rollback();
            logger.error("Nie udało się dodać projektu" + e.getMessage());
        }finally{

            entityManager.close();

        }


//        String query = "INSERT INTO projekt (nazwa, opis, data_oddania, dataczas_utworzenia) VALUES (?, ?, ?, ?)";
//
//        try {
//            entityManager.createNativeQuery(query).setParameter(1, projekt.getNazwa())
//                    .setParameter(2, projekt.getOpis())
//                    .setParameter(3, projekt.getDataOddania())
//                    .setParameter(4, new LocalDateTime[]{LocalDateTime.now()})
//                    .executeUpdate();
//
//
//        }catch(NullPointerException e){
//            logger.error("Could not get entity manager NullPointerException: " + e.getMessage());
//        }
//        entityManager.close();
    }
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {

        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Projekt projekt = entityManager.find(Projekt.class, projektId);
            entityManager.remove(projekt);
            transaction.commit();


        }catch(NullPointerException e){
            transaction.rollback();
            logger.error("Could not get any project with matching ID NullPointerException: " + e.getMessage());
        }
        finally {

            entityManager.close();
        }

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
