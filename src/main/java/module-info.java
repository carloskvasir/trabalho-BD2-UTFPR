    module trabalho_banco_2.trabalhobd2 {
        requires javafx.controls;
        requires javafx.fxml;

        requires com.dlsc.formsfx;
        requires java.sql;

        opens view to javafx.fxml;
        exports view;
    }