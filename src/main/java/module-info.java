    module trabalho_banco_2.trabalhobd2 {
        requires javafx.controls;
        requires javafx.fxml;

        requires com.dlsc.formsfx;
        requires java.sql;

        opens trabalho_banco_2.trabalhobd2 to javafx.fxml;
        exports trabalho_banco_2.trabalhobd2;
    }