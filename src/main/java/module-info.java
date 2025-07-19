module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.example.tictactoe to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.tictactoe;
}