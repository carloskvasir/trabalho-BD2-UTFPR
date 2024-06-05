package trabalho_banco_2.trabalhobd2;

import DB_Conection.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            ConnectionFactory.getConnection(username, password);
            messageLabel.setText("Deu boa guri");
        } catch (SQLException e) {
            messageLabel.setText("Erro ao conectar no banco");
            e.printStackTrace();
        }
    }
}



