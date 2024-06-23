package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaUtil {

    /**
     * Exibe um alerta com uma mensagem de informação.
     * @param titulo O título do alerta.
     * @param cabecalho O cabeçalho do alerta.
     * @param mensagem A mensagem principal do alerta.
     */
    public static void mostrarAlertaInformacao(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /**
     * Exibe um alerta com uma mensagem de aviso.
     * @param titulo O título do alerta.
     * @param cabecalho O cabeçalho do alerta.
     * @param mensagem A mensagem principal do alerta.
     */
    public static void mostrarAlertaAviso(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /**
     * Exibe um alerta com uma mensagem de erro.
     * @param titulo O título do alerta.
     * @param cabecalho O cabeçalho do alerta.
     * @param mensagem A mensagem principal do alerta.
     */
    public static void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /**
     * Exibe um alerta de confirmação.
     * @param titulo O título do alerta.
     * @param cabecalho O cabeçalho do alerta.
     * @param mensagem A mensagem principal do alerta.
     */
    public static void mostrarAlertaConfirmacao(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
