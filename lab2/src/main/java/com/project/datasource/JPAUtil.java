package com.project.datasource;

import com.project.app.ProjectClientApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final Logger logger = LoggerFactory.getLogger(ProjectClientApplication.class);
    private static final String DATA_SOURCE = "com.project.datasource";//nazwa persistence-unit z pliku persistence.xml
    private static final EntityManagerFactory entityManagerFactory;
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(DATA_SOURCE);
    }
    private JPAUtil() {}

    public static EntityManager getEntityManager() {
    try {
        return entityManagerFactory.createEntityManager();
    }catch(Exception e){
        logger.error("JPAUtil.getEntityManager(): " + e);
        return null;

    }

    }

    public static void close() {
        entityManagerFactory.close();
    }
}
