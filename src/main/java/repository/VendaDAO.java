package repository;

import DB_Conection.ConnectionFactory;
import domain.ItemVenda;
import domain.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public void createVenda(Venda venda) throws SQLException {
        String insertVendaQuery = "INSERT INTO tb_vendas (ven_horario, ven_valor_total, tb_funcionario_fun_codigo) VALUES (?, ?, ?)";
        String insertItemQuery = "INSERT INTO tb_itens_vendidos (ven_codigo, pro_codigo, item_quantidade, item_valor) VALUES (?, ?, ?, ?)";
        String updateProdutoQuery = "UPDATE tb_produtos SET pro_quantidade = pro_quantidade - ? WHERE pro_codigo = ?";
        String checkEstoqueQuery = "SELECT pro_quantidade FROM tb_produtos WHERE pro_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement vendaStmt = conn.prepareStatement(insertVendaQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement itemStmt = conn.prepareStatement(insertItemQuery);
             PreparedStatement updateProdutoStmt = conn.prepareStatement(updateProdutoQuery);
             PreparedStatement checkEstoqueStmt = conn.prepareStatement(checkEstoqueQuery)) {

            conn.setAutoCommit(false);

            // Verificar estoque disponível
            for (ItemVenda item : venda.getItens()) {
                checkEstoqueStmt.setLong(1, item.getProdutoCodigo());
                ResultSet rsEstoque = checkEstoqueStmt.executeQuery();
                if (rsEstoque.next()) {
                    int estoqueDisponivel = rsEstoque.getInt("pro_quantidade");
                    if (estoqueDisponivel < item.getQuantidade()) {
                        throw new SQLException("Estoque insuficiente para o produto código: " + item.getProdutoCodigo());
                    }
                } else {
                    throw new SQLException("Produto não encontrado: " + item.getProdutoCodigo());
                }
            }

            // Inserir a venda
            vendaStmt.setTimestamp(1, Timestamp.valueOf(venda.getHorario()));
            vendaStmt.setDouble(2, venda.getValorTotal());
            vendaStmt.setLong(3, venda.getFuncionarioCodigo());
            vendaStmt.executeUpdate();

            // Obter o ID gerado da venda
            ResultSet rs = vendaStmt.getGeneratedKeys();
            long vendaId = 0;
            if (rs.next()) {
                vendaId = rs.getLong(1);
            }

            // Inserir itens da venda e atualizar o estoque
            for (ItemVenda item : venda.getItens()) {
                itemStmt.setLong(1, vendaId);
                itemStmt.setLong(2, item.getProdutoCodigo());
                itemStmt.setInt(3, item.getQuantidade());
                itemStmt.setDouble(4, item.getValor());
                itemStmt.addBatch();

                updateProdutoStmt.setInt(1, item.getQuantidade());
                updateProdutoStmt.setLong(2, item.getProdutoCodigo());
                updateProdutoStmt.addBatch();
            }
            itemStmt.executeBatch();
            updateProdutoStmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Venda getVenda(long vendaCodigo) {
        Venda venda = null;
        String queryVenda = "SELECT * FROM tb_vendas WHERE ven_codigo = ?";
        String queryItens = "SELECT * FROM tb_itens_vendidos WHERE ven_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement vendaStmt = conn.prepareStatement(queryVenda);
             PreparedStatement itensStmt = conn.prepareStatement(queryItens)) {

            // Obter a venda
            vendaStmt.setLong(1, vendaCodigo);
            ResultSet rsVenda = vendaStmt.executeQuery();
            if (rsVenda.next()) {
                venda = new Venda(
                        rsVenda.getLong("ven_codigo"),
                        rsVenda.getTimestamp("ven_horario").toLocalDateTime(),
                        rsVenda.getDouble("ven_valor_total"),
                        rsVenda.getLong("tb_funcionario_fun_codigo"),
                        new ArrayList<>()
                );
            }

            // Obter os itens da venda
            if (venda != null) {
                itensStmt.setLong(1, vendaCodigo);
                ResultSet rsItens = itensStmt.executeQuery();
                while (rsItens.next()) {
                    venda.getItens().add(new ItemVenda(
                            rsItens.getLong("item_codigo"),
                            rsItens.getLong("ven_codigo"),
                            rsItens.getLong("pro_codigo"),
                            rsItens.getInt("item_quantidade"),
                            rsItens.getDouble("item_valor")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venda;
    }

    public void updateVenda(Venda venda) throws SQLException {
        String updateVendaQuery = "UPDATE tb_vendas SET ven_horario = ?, ven_valor_total = ?, tb_funcionario_fun_codigo = ? WHERE ven_codigo = ?";
        String deleteItemsQuery = "DELETE FROM tb_itens_vendidos WHERE ven_codigo = ?";
        String insertItemQuery = "INSERT INTO tb_itens_vendidos (ven_codigo, pro_codigo, item_quantidade, item_valor) VALUES (?, ?, ?, ?)";
        String updateProdutoQuery = "UPDATE tb_produtos SET pro_quantidade = pro_quantidade + ? WHERE pro_codigo = ?";
        String checkEstoqueQuery = "SELECT pro_quantidade FROM tb_produtos WHERE pro_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement updateVendaStmt = conn.prepareStatement(updateVendaQuery);
             PreparedStatement deleteItemsStmt = conn.prepareStatement(deleteItemsQuery);
             PreparedStatement insertItemStmt = conn.prepareStatement(insertItemQuery);
             PreparedStatement updateProdutoStmt = conn.prepareStatement(updateProdutoQuery);
             PreparedStatement checkEstoqueStmt = conn.prepareStatement(checkEstoqueQuery)) {

            conn.setAutoCommit(false);

            // Atualizar a venda
            updateVendaStmt.setTimestamp(1, Timestamp.valueOf(venda.getHorario()));
            updateVendaStmt.setDouble(2, venda.getValorTotal());
            updateVendaStmt.setLong(3, venda.getFuncionarioCodigo());
            updateVendaStmt.setLong(4, venda.getCodigo());
            updateVendaStmt.executeUpdate();

            // Devolver estoque de itens antigos
            deleteItemsStmt.setLong(1, venda.getCodigo());
            ResultSet rsOldItems = deleteItemsStmt.executeQuery();
            while (rsOldItems.next()) {
                long produtoCodigo = rsOldItems.getLong("pro_codigo");
                int quantidade = rsOldItems.getInt("item_quantidade");

                updateProdutoStmt.setInt(1, quantidade);
                updateProdutoStmt.setLong(2, produtoCodigo);
                updateProdutoStmt.addBatch();
            }
            updateProdutoStmt.executeBatch();

            // Deletar itens antigos
            deleteItemsStmt.executeUpdate();

            // Verificar estoque disponível para novos itens
            for (ItemVenda item : venda.getItens()) {
                checkEstoqueStmt.setLong(1, item.getProdutoCodigo());
                ResultSet rsEstoque = checkEstoqueStmt.executeQuery();
                if (rsEstoque.next()) {
                    int estoqueDisponivel = rsEstoque.getInt("pro_quantidade");
                    if (estoqueDisponivel < item.getQuantidade()) {
                        throw new SQLException("Estoque insuficiente para o produto código: " + item.getProdutoCodigo());
                    }
                } else {
                    throw new SQLException("Produto não encontrado: " + item.getProdutoCodigo());
                }
            }

            // Inserir novos itens e atualizar estoque
            for (ItemVenda item : venda.getItens()) {
                insertItemStmt.setLong(1, venda.getCodigo());
                insertItemStmt.setLong(2, item.getProdutoCodigo());
                insertItemStmt.setInt(3, item.getQuantidade());
                insertItemStmt.setDouble(4, item.getValor());
                insertItemStmt.addBatch();

                updateProdutoStmt.setInt(1, -item.getQuantidade());
                updateProdutoStmt.setLong(2, item.getProdutoCodigo());
                updateProdutoStmt.addBatch();
            }
            insertItemStmt.executeBatch();
            updateProdutoStmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteVenda(long vendaCodigo) throws SQLException {
        String deleteItemsQuery = "DELETE FROM tb_itens_vendidos WHERE ven_codigo = ?";
        String deleteVendaQuery = "DELETE FROM tb_vendas WHERE ven_codigo = ?";
        String updateProdutoQuery = "UPDATE tb_produtos SET pro_quantidade = pro_quantidade + ? WHERE pro_codigo = ?";
        String queryItens = "SELECT * FROM tb_itens_vendidos WHERE ven_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement deleteItemsStmt = conn.prepareStatement(deleteItemsQuery);
             PreparedStatement deleteVendaStmt = conn.prepareStatement(deleteVendaQuery);
             PreparedStatement updateProdutoStmt = conn.prepareStatement(updateProdutoQuery);
             PreparedStatement queryItensStmt = conn.prepareStatement(queryItens)) {

            conn.setAutoCommit(false);

            // Obter itens da venda para atualizar o estoque
            queryItensStmt.setLong(1, vendaCodigo);
            ResultSet rsItens = queryItensStmt.executeQuery();
            while (rsItens.next()) {
                long produtoCodigo = rsItens.getLong("pro_codigo");
                int quantidade = rsItens.getInt("item_quantidade");

                updateProdutoStmt.setInt(1, quantidade);
                updateProdutoStmt.setLong(2, produtoCodigo);
                updateProdutoStmt.addBatch();
            }
            updateProdutoStmt.executeBatch();

            // Deletar itens da venda
            deleteItemsStmt.setLong(1, vendaCodigo);
            deleteItemsStmt.executeUpdate();

            // Deletar a venda
            deleteVendaStmt.setLong(1, vendaCodigo);
            deleteVendaStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
