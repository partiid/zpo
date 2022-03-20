module com.project.lab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.lab2 to javafx.fxml;
    exports com.project.lab2;
}