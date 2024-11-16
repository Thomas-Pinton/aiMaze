module com.example.labirintojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labirintojavafx to javafx.fxml;
    exports com.example.labirintojavafx;
}