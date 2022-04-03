module com.project.lab2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens com.project.lab2 to javafx.fxml;
    exports com.project.lab2;
}