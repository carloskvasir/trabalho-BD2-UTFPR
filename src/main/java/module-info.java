    module trabalho_banco_2.trabalhobd2 {
        requires javafx.controls;
        requires javafx.fxml;

        requires com.dlsc.formsfx;
        requires java.sql;
        requires static lombok;
        requires com.fasterxml.jackson.core;
        requires com.fasterxml.jackson.databind;
        requires org.json;

        opens view to javafx.fxml;
        exports view;
    }