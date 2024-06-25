package view;

import DB_Conection.CurrentUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import domain.ItemVenda;
import domain.Produto;
import domain.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import service.ProdutoService;
import service.VendaService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaScreen {

    @FXML
    private TextField input_prod_name;
    @FXML
    private Button buscar_prod;
    @FXML
    private ScrollPane resultadoVenda;
    @FXML
    private ListView<Produto> list_prod;
    @FXML
    private Button add_prod;
    @FXML
    private ScrollPane cestaList;
    @FXML
    private ListView<String> cesta;
    @FXML
    private Spinner<Integer> quantidade_produto;

    private ObservableList<Produto> produtosEncontrados;
    private ObservableList<String> cestaVisualizacao;
    private List<Produto> produtosListCesta;
    private ProdutoService produtoService;
    private double valorTotal;
    private List<ItemVenda> listItem = new ArrayList<>();

    @FXML
    private void onBuscarProdutoClicked(MouseEvent event) {
        String nomeProduto = input_prod_name.getText();
        buscarProduto(nomeProduto);
    }

    private void buscarProduto(String nomeProduto) {
        produtosEncontrados.clear();
        List<Produto> produtosList = produtoService.findByName(nomeProduto);

        if (produtosList != null && !produtosList.isEmpty()) {
            produtosEncontrados.addAll(produtosList);
        } else {
            System.out.println("Nenhum produto encontrado com o nome: " + nomeProduto);
        }
    }

    @FXML
    private void onAdicionarProdutoClicked(MouseEvent event) {
        Produto produtoSelecionado = list_prod.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            System.out.println("Nenhum produto selecionado.");
            return;
        }

        int quantidade = quantidade_produto.getValue();
        if (quantidade > produtoSelecionado.getQuantidade()) {
            System.out.println("Quantidade solicitada excede o estoque disponível.");
            return;
        }

        double valorParcial = BigDecimal.valueOf(produtoSelecionado.getValor())
                .multiply(BigDecimal.valueOf(quantidade))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();        valorTotal += valorParcial; // Acumula o valor total

        produtosListCesta.add(produtoSelecionado);
        listItem.add(ItemVenda.builder()
                .produtoCodigo(produtoSelecionado.getCodigo())
                .vendaCodigo(0L) // Será atualizado após a inserção da venda
                .quantidade(quantidade)
                .valor(valorParcial)
                .build());

        cestaVisualizacao.add(produtoSelecionado.getDescricao() + " - Quantidade: " + quantidade + " - Valor: " + valorParcial);
    }

    @FXML
    public void onFinalizarVendaClicked(MouseEvent event){

        Venda venda = Venda.builder()
                .funcionarioCodigo(CurrentUser.getInstance().getId())
                .horario(LocalDate.now())
                .valorTotal(valorTotal)
                .build();

        try {
            VendaService.inserirVendaComItens(venda, listItem);
            AlertaUtil.mostrarAlertaSucesso("Sucesso", "Venda finalizada com sucesso");
            produtosListCesta.clear();
            cestaVisualizacao.clear();
            listItem.clear();
            valorTotal = 0.0;
        } catch (SQLException | JsonProcessingException e) {
            AlertaUtil.mostrarAlertaErro("Erro ao Finalizar Venda", "Ocorreu um erro ao finalizar a venda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onRemoverProdutoClicked(MouseEvent event) {
        String itemSelecionado = cesta.getSelectionModel().getSelectedItem();
        if (itemSelecionado == null) {
            System.out.println("Nenhum item selecionado na cesta.");
            return;
        }

        produtosListCesta.removeIf(produto -> itemSelecionado.contains(produto.getDescricao()));
        cestaVisualizacao.remove(itemSelecionado);

        valorTotal = 0.0;
        for (ItemVenda item : listItem) {
            valorTotal += item.getQuantidade() * item.getValor();
        }
    }

    @FXML
    private void initialize() {
        produtosEncontrados = FXCollections.observableArrayList();
        cestaVisualizacao = FXCollections.observableArrayList();
        produtosListCesta = new ArrayList<>();
        list_prod.setItems(produtosEncontrados);
        cesta.setItems(cestaVisualizacao);
        produtoService = new ProdutoService();
        quantidade_produto.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        list_prod.setCellFactory(new Callback<ListView<Produto>, ListCell<Produto>>() {
            @Override
            public ListCell<Produto> call(ListView<Produto> listView) {
                return new ListCell<Produto>() {
                    @Override
                    protected void updateItem(Produto produto, boolean empty) {
                        super.updateItem(produto, empty);
                        if (empty || produto == null) {
                            setText(null);
                        } else {
                            setText(produto.getDescricao() + " - Quantidade: " + produto.getQuantidade() + " - Valor: " + produto.getValor());
                        }
                    }
                };
            }
        });
    }

    public void onFinalizarVendaClickedError(MouseEvent event) {
        Venda venda = Venda.builder()
                .funcionarioCodigo(777777777)
                .horario(LocalDate.now())
                .valorTotal(valorTotal)
                .build();

        try {
            VendaService.inserirVendaComItens(venda, listItem);
            produtosListCesta.clear();
            cestaVisualizacao.clear();
            listItem.clear();
            valorTotal = 0.0;
        } catch (SQLException | JsonProcessingException e) {
            AlertaUtil.mostrarAlertaErro("Erro ao Finalizar Venda", "Ocorreu um erro ao finalizar a venda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onGoBack(ActionEvent actionEvent) {
        NavigationUtil.goBack(actionEvent);
    }
}
