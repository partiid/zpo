package com.project.app;



import com.project.controller.ProjectController;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;

public class ProjectClientApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(ProjectClientApplication.class);

    private Parent root;
    private FXMLLoader fxmlLoader;
    public static void main(String[] args) {
        launch(ProjectClientApplication.class, args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/ProjectFrame.fxml"));
        root = fxmlLoader.load();
        primaryStage.setTitle("Projekty");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        ProjectController controller = fxmlLoader.getController();
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.hide();
            controller.shutdown();
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        ProjektDAO projektDAO = new ProjektDAO();
//
//        logger.info("Uruchamianie..." + LocalDate.now());
//
//
//        try {
//            Projekt projekt = new Projekt("Projekt testowy 4", "Opis testowy 2", LocalDate.of(2020, 6, 22));
//                    projektDAO.setProjekt(projekt);
//
//            logger.info("Id utworzonego projektu: {}", projekt.getProjektId());
//
////System.out.println("Id utworzonego projektu: " + projekt.getProjektId());
//        } catch (RuntimeException e) {
//            logger.error("Błąd podczas dodawania projektu!", e);
//        }
//
//        //todo pobieranie danych
//
//        try {
//            int projektId = 2;
//            Projekt projekt = projektDAO.getProjekt(projektId);
//
//        }catch(RuntimeException e){
//            logger.error("Błąd podczas pobierania projektu!", e);
//        }
//
//    }
}
