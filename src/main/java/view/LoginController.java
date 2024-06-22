package view;

import DB_Conection.ConnectionFactory;
import DB_Conection.CurrentUser;
import domain.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repository.ProdutoDAO;

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

        ProdutoDAO p = new ProdutoDAO();
        System.out.println(p.getAllProdutos());

    }

    private void tryLogin(String username, String password, ActionEvent event) {
        try {
            storeCurrentUser(username, password);
            ConnectionFactory.getConnection();
            NavigationUtil.navigateToScreen(event, "vendaScreen.fxml");
        } catch (SQLException | IOException e) {
            displayError("Erro ao conectar no banco", e);
        }
    }



    private void storeCurrentUser(String username, String password) {
        CurrentUser currentUser = CurrentUser.getInstance();
        currentUser.setUsername(username);
        currentUser.setPassword(password);
    }

    private void displayError(String message, Exception e) {
        messageLabel.setText(message);
        e.printStackTrace();
    }
}
