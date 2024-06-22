package view;

import DB_Conection.CurrentUser;
import domain.ItemVenda;
import domain.Produto;
import domain.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import repository.ItemVendaDAO;
import repository.VendaDAO;
import service.ProdutoService;

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
    private ScrollPane resultadoVenda1;
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
        if (nomeProduto.isEmpty()) {
            System.out.println("Nome do produto não pode estar vazio.");
            return;
        }

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

        double valorParcial = quantidade * produtoSelecionado.getValor();
        valorTotal += valorParcial; // Acumula o valor total

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
        VendaDAO vendaDAO = new VendaDAO();

        Venda venda = Venda.builder()
                .funcionarioCodigo(CurrentUser.getInstance().getId())
                .horario(LocalDate.now())
                .valorTotal(valorTotal)
                .build();

        Long idVenda = vendaDAO.insert(venda);

        for (ItemVenda item : listItem) {
            item.setVendaCodigo(idVenda); // Atualiza o ID da venda em cada item
        }

        ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
        itemVendaDAO.insert(listItem);

        // Limpa a cesta após finalizar a venda
        produtosListCesta.clear();
        cestaVisualizacao.clear();
        listItem.clear();
        valorTotal = 0.0;
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

        // Atualiza o valor total após remoção
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
}
