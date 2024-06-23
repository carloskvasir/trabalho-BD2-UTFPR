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

    // Navega para uma nova tela
    public static void navigateToScreen(ActionEvent event, String fxmlFileName) throws IOException {
        // Obtém o palco (stage) atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Guarda a cena atual na pilha
        sceneStack.push(stage.getScene());

        // Carrega a nova tela a partir do FXML
        Parent root = FXMLLoader.load(NavigationUtil.class.getResource(fxmlFileName));

        // Verifica se o carregamento foi bem-sucedido
        if (root != null) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Falha ao carregar a tela: " + fxmlFileName);
        }
    }

    // Volta para a tela anterior
    public static void goBack(ActionEvent event) {
        // Verifica se há cenas na pilha para voltar
        if (!sceneStack.isEmpty()) {
            // Obtém o palco (stage) atual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remove e obtém a última cena da pilha
            Scene previousScene = sceneStack.pop();

            // Define a cena anterior como a cena atual
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.err.println("Nenhuma cena anterior disponível para voltar.");
        }
    }
}
