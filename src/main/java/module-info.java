module com.example.labs_tasks1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labs_tasks1 to javafx.fxml;
    exports com.example.labs_tasks1;
}