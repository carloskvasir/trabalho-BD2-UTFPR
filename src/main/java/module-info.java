    module trabalho_banco_2.trabalhobd2 {
        requires javafx.controls;
        requires javafx.fxml;

            opens domain to com.fasterxml.jackson.databind;

            requires com.dlsc.formsfx;
        requires java.sql;
        requires static lombok;
        requires com.fasterxml.jackson.core;
        requires com.fasterxml.jackson.databind;
        requires org.json;
        requires org.postgresql.jdbc;

        opens view to javafx.fxml;
        exports view;
    }