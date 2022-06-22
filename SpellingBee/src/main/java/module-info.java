module com.example.spellingbee {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spellingbee to javafx.fxml;
    exports com.example.spellingbee;
}