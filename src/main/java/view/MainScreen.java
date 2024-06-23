package view;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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
}
