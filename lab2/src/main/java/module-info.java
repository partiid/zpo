module com.project.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.slf4j;
    requires java.sql;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires net.bytebuddy;
    requires java.transaction;

    opens com.project.lab2 to org.hibernate.orm.core, javafx.base;


    exports com.project.app to javafx.graphics;

    exports com.project.controller to javafx.graphics;
    opens com.project.controller to javafx.fxml;


    exports com.project.datasource;
    opens com.project.datasource to javafx.fxml;
}