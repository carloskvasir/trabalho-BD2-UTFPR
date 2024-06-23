package view;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import service.Funcao3a;

import java.io.IOException;
import java.sql.SQLException;

public class MainScreen {
    public void onNavigateToVenda(ActionEvent event) {
        try {
            NavigationUtil.navigateToScreen(event, "vendaScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNavigateToRelatorio(ActionEvent event) {
        try {
            NavigationUtil.navigateToScreen(event, "relatorio.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackup(ActionEvent event) {
        try {
            NavigationUtil.navigateToScreen(event, "vendaScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onRollback(MouseEvent event) {
        try {
            Funcao3a.callTentarInserirComRollback(999929876L,"test","122323423","test", "test", "test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
