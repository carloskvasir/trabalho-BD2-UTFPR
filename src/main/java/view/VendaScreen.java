package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class MainScreen {

    @FXML
    private TextField pesquisaVenda;
    @FXML
    private Button buscarVenda;
    @FXML
    private ScrollPane resultadoVenda;
    @FXML
    private ListView<?> resultadoVendaList;
    @FXML
    private Button buscarVenda1;
    @FXML
    private ScrollPane resultadoVenda1;
    @FXML
    private ListView<?> resultadoVenda1List;
    @FXML
    private Button buscarVenda11;
    @FXML
    private Label cestaLabel;
    @FXML
    private void initialize() {
    }

    @FXML
    private void onBuscarVendaClicked(MouseEvent event) {
        System.out.println("Buscar venda: " + pesquisaVenda.getText());
    }

    @FXML
    private void onAdicionarProdutoClicked(MouseEvent event) {
        System.out.println("Adicionar produto");
    }

    @FXML
    private void onFinalizarVendaClicked(MouseEvent event) {
        // Lógica para finalizar venda
        System.out.println("Finalizar venda");
    }
}
