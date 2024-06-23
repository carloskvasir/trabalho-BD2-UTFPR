package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaUtil {

    public static void mostrarAlertaInformacao(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarAlertaAviso(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }


    public static void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarAlertaConfirmacao(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.NONE);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarAlertaSucesso(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensagem);
        alerta.setContentText(null);
        alerta.showAndWait();
    }
}
