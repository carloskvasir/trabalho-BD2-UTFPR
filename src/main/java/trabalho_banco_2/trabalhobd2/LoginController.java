package trabalho_banco_2.trabalhobd2;

import DB_Conection.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        tryLogin(username, password, event);
    }

    private void tryLogin(String username, String password, ActionEvent event) {
        try {
            connectToDatabase(username, password);
            navigateToMainScreen(event);
        } catch (SQLException | IOException e) {
            displayError("Erro ao conectar no banco", e);
        }
    }

    private void connectToDatabase(String username, String password) throws SQLException {
        ConnectionFactory.getConnection(username, password);
    }

    private void navigateToMainScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 300, 200);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void displayError(String message, Exception e) {
        messageLabel.setText(message);
        e.printStackTrace();
    }
}
