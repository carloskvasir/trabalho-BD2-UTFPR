package view;

import domain.ItemVenda;
import domain.Produto;
import domain.Venda;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.ProdutoDAO;
import repository.VendaDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
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
    private ListView<String> list_prod;
    @FXML
    private Button add_prod;
    @FXML
    private ScrollPane resultadoVenda1;
    @FXML
    private ListView<String> cesta;
    @FXML
    private Button finaliza_venda;
    @FXML
    private Label cestaLabel;

    private ObservableList<String> produtosEncontrados;
    private ObservableList<String> itensVenda;
    private List<Produto> produtosList;
    private ProdutoDAO produtoDAO;
    private VendaDAO vendaDAO;

    @FXML
    private void initialize() {
        produtosEncontrados = FXCollections.observableArrayList();
        itensVenda = FXCollections.observableArrayList();
        list_prod.setItems(produtosEncontrados);
        cesta.setItems(itensVenda);

        produtoDAO = new ProdutoDAO();
        vendaDAO = new VendaDAO();
    }

    @FXML
    private void onBuscarProdutoClicked(MouseEvent event) {
        String nomeProduto = input_prod_name.getText();
        if (nomeProduto.isEmpty()) {
            System.out.println("Nome do produto não pode estar vazio.");
            return;
        }

        produtosList = produtoDAO.findByName(nomeProduto);

        produtosEncontrados.clear();
        for (Produto produto : produtosList) {
            produtosEncontrados.add(produto.getDescricao() + " - Quantidade: " + produto.getQuantidade() + " - Valor: " + produto.getValor());
        }
        if (produtosEncontrados.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o nome: " + nomeProduto);
        }
    }

    @FXML
    private void onAdicionarProdutoClicked(MouseEvent event) {
        String produtoSelecionado = list_prod.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            System.out.println("Nenhum produto selecionado.");
            return;
        }

        // Encontra o produto na lista original
        Produto produto = produtosList.stream()
                .filter(p -> produtoSelecionado.contains(p.getDescricao()))
                .findFirst()
                .orElse(null);

        if (produto != null) {
            int quantidade = 1; // LOGICA DE QUANTIDADE
            double valor = produto.getValor();

            ItemVenda item = new ItemVenda(0, 0, produto.getCodigo(), quantidade, valor);
            itensVenda.add(produto.getDescricao() + " - Quantidade: " + quantidade + " - Valor: " + valor);

            // Atualiza a quantidade do produto na lista original
            produto.setQuantidade(produto.getQuantidade() - quantidade);
        }
    }

    @FXML
    private void onFinalizarVendaClicked(MouseEvent event) {
        List<ItemVenda> itens = new ArrayList<>();
        for (String itemDesc : itensVenda) {
            String[] parts = itemDesc.split(" - ");
            String descricao = parts[0];
            int quantidade = Integer.parseInt(parts[1].split(": ")[1]);
            double valor = Double.parseDouble(parts[2].split(": ")[1]);

            Produto produto = produtosList.stream()
                    .filter(p -> descricao.contains(p.getDescricao()))
                    .findFirst()
                    .orElse(null);

            if (produto != null) {
                itens.add(new ItemVenda(0, 0, produto.getCodigo(), quantidade, valor));
            }
        }

        double total = calcularTotal(itens);
        long funcionarioCodigo = obterFuncionarioCodigo();
        Venda venda = new Venda(0, LocalDateTime.now(), total, funcionarioCodigo, itens);

        try {
            vendaDAO.createVenda(venda);
            itensVenda.clear();
            produtosEncontrados.clear();
            list_prod.refresh();
            cesta.refresh();
            System.out.println("Venda finalizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao finalizar venda: " + e.getMessage());
        }
    }

    private long obterFuncionarioCodigo() {
        // Lógica para obter o código do funcionário atual
        return 123; // Substitua pelo valor correto
    }

    private double calcularTotal(List<ItemVenda> itens) {
        return itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getValor())
                .sum();
    }
}
