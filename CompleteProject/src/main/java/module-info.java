module com.example.finalprojectever {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalprojectever to javafx.fxml;
    exports com.example.finalprojectever;
}