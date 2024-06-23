package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class NavigationUtil {

    private static final Stack<Scene> sceneStack = new Stack<>();

    public static void navigateToScreen(ActionEvent event, String fxmlFileName) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene());
        Parent root = FXMLLoader.load(NavigationUtil.class.getResource(fxmlFileName));
        if (root != null) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Falha ao carregar a tela: " + fxmlFileName);
        }
    }

    public static void goBack(ActionEvent event) {
        if (!sceneStack.isEmpty()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene previousScene = sceneStack.pop();
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.err.println("Nenhuma cena anterior disponível para voltar.");
        }
    }
}
