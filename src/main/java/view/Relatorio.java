package view;

import dao.RelatorioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import service.RelatorioService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Relatorio {

    @FXML
    private ScrollPane resultadoVenda;

    @FXML
    private ListView<String> list_prod;

    @FXML
    private DatePicker data;

    @FXML
    private DatePicker data1;

    @FXML
    private Button voltarButton;

    @FXML
    private Button gerarButton;

    // Formato de data desejado
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private RelatorioDAO relatorioDAO = new RelatorioDAO();

    @FXML
    void initialize() {
        // Configurações para garantir que o ListView ocupe todo o espaço do ScrollPane
        list_prod.setPrefHeight(Double.MAX_VALUE);
        list_prod.setPrefWidth(Double.MAX_VALUE);
        resultadoVenda.setFitToHeight(true);
        resultadoVenda.setFitToWidth(true);
    }

    @FXML
    void onVoltar(ActionEvent event) {
        // Lógica para voltar à tela anterior
        NavigationUtil.goBack(event);
    }

    @FXML
    void onGerar(ActionEvent event) {
        LocalDate startDate = data.getValue();
        LocalDate endDate = data1.getValue();

        if (startDate == null || endDate == null) {
            showAlert(AlertType.ERROR, "Erro", "Por favor, selecione ambas as datas.");
            return;
        }

        if (startDate.isAfter(endDate)) {
            showAlert(AlertType.ERROR, "Erro", "A data inicial deve ser anterior ou igual à data final.");
            return;
        }

        list_prod.getItems().clear();

        List<String> resultado = null;
        try {
            List<String> relatorio = relatorioDAO.getRelatorioVendasPeriodo(startDate, endDate);
            list_prod.getItems().addAll(relatorio);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro de Banco de Dados", "Ocorreu um erro ao gerar o relatório: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
