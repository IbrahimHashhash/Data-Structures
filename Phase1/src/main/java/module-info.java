module com.example.phase1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.phase1 to javafx.fxml;
    exports all;
    opens all to javafx.fxml;
}