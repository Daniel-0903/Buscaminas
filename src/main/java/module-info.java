module com.example.buscaminas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens com.example.buscaminas to javafx.fxml;
    exports com.example.buscaminas;
}