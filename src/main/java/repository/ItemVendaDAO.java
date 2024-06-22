package repository;

import DB_Conection.ConnectionFactory;
import domain.ItemVenda;

import java.sql.*;
import java.util.List;

public class ItemVendaDAO {

    public void insert(List<ItemVenda> itemVenda) {
        Long idVenda = null;
        String sqlInsertItem = "INSERT INTO tb_itens (ite_quantidade, ite_valor_parcial, tb_produtos_pro_codigo, tb_venda_ven_codigo) VALUES (?, ?, ?, ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertItem)) {

            for (ItemVenda it : itemVenda) {
                stmt.setInt(1, it.getQuantidade());
                stmt.setDouble(2, it.getValor());
                stmt.setLong(3, it.getProdutoCodigo());
                stmt.setLong(4, it.getVendaCodigo());

                stmt.addBatch();
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
