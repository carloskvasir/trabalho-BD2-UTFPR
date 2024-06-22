package view;

import DB_Conection.CurrentUser;
import domain.ItemVenda;
import domain.Produto;
import domain.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import repository.FuncionarioDAO;
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
    private ListView<Produto> list_prod; // Exibir Produto diretamente
    @FXML
    private Button add_prod;
    @FXML
    private ScrollPane resultadoVenda1;
    @FXML
    private ListView<String> cesta; // Exibir descrição simples

    private ObservableList<Produto> produtosEncontrados;
    private ObservableList<String> cestaVisualizacao; // Para exibição produtos cesta
    private List<Produto> produtosListCesta; // Lista de produtos adicionados ao carrinho
    private ProdutoService produtoService;
    private List<Produto> produtosList;
    private double valorTotal;
    List<ItemVenda> listItem = new ArrayList<>();

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
        produtosList = produtoService.findByName(nomeProduto);

        adicionaProdutoEncontrado(nomeProduto);
    }



    @FXML
    private void onAdicionarProdutoClicked(MouseEvent event) {
        Produto produtoSelecionado = list_prod.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            System.out.println("Nenhum produto selecionado.");
            return;
        }

        int quantidade = 1;
        valorTotal = quantidade * produtoSelecionado.getValor();

        produtosListCesta.add(produtoSelecionado);
        addVisualizacaoProdutosNaCesta(produtoSelecionado, quantidade, valorTotal);
    }

    private void addVisualizacaoProdutosNaCesta(Produto produtoSelecionado, int quantidade, double valorTotal) {
        cestaVisualizacao.add(produtoSelecionado.getDescricao() + " - Quantidade: " + quantidade + " - Valor: " + valorTotal);
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

        for (Produto produto : produtosListCesta) {
            listItem.add(ItemVenda.builder()
                    .produtoCodigo(produto.getCodigo())
                    .vendaCodigo(idVenda)
                    .quantidade(produto.getQuantidade())
                    .valor(produto.getValor())
                    .build());
        }

        ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
        itemVendaDAO.insert(listItem);

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
    }

    private void adicionaProdutoEncontrado(String nomeProduto) {
        if (produtosList != null && !produtosList.isEmpty()) {
            produtosEncontrados.addAll(produtosList);
        } else {
            System.out.println("Nenhum produto encontrado com o nome: " + nomeProduto);
        }
    }


    @FXML
    private void initialize() {
        produtosEncontrados = FXCollections.observableArrayList();
        cestaVisualizacao = FXCollections.observableArrayList();
        produtosListCesta = FXCollections.observableArrayList();
        list_prod.setItems(produtosEncontrados);
        cesta.setItems(cestaVisualizacao);
        produtoService = new ProdutoService();
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
