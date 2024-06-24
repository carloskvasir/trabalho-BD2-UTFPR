package dao;

import DB_Conection.ConnectionFactory;
import domain.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM tb_produtos";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                long codigo = rs.getLong("pro_codigo");
                String descricao = rs.getString("pro_descricao");
                double valor = rs.getDouble("pro_valor");
                int quantidade = rs.getInt("pro_quantidade");
                long fornecedorCodigo = rs.getLong("tb_fornecedores_for_codigo");

                produtos.add(new Produto(codigo, descricao, valor, quantidade, fornecedorCodigo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public List<Produto> findByName(String name) {
        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM tb_produtos WHERE pro_descricao LIKE ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long codigo = rs.getLong("pro_codigo");
                String descricao = rs.getString("pro_descricao");
                double valor = rs.getDouble("pro_valor");
                int quantidade = rs.getInt("pro_quantidade");
                long fornecedorCodigo = rs.getLong("tb_fornecedores_for_codigo");

                produtos.add(new Produto(codigo, descricao, valor, quantidade, fornecedorCodigo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void updateEstoque(long produtoCodigo, int quantidadeVendida) throws SQLException {
        String query = "UPDATE tb_produtos SET pro_quantidade = pro_quantidade - ? WHERE pro_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, quantidadeVendida);
            stmt.setLong(2, produtoCodigo);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o estoque, nenhum produto foi encontrado com o código fornecido.");
            }
        }
    }

}
