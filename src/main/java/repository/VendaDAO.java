package repository;

import DB_Conection.ConnectionFactory;
import domain.Funcionario;
import domain.Venda;

import java.sql.*;

public class VendaDAO {

    public Long insert(Venda venda) {
        Long idVenda = null;
        String sqlInsertVenda = "INSERT INTO tb_vendas (ven_horario, ven_valor_total, tb_funcionario_fun_codigo) VALUES (?, ?, ?) RETURNING ven_codigo";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsertVenda)) {

            stmt.setDate(1, Date.valueOf(venda.getHorario()));
            stmt.setDouble(2, venda.getValorTotal());
            stmt.setLong(3, venda.getFuncionarioCodigo());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idVenda = rs.getLong("ven_codigo");
                    venda.setCodigo(idVenda);
                    return idVenda;
                } else {
                    throw new SQLException("Falha ao inserir a venda, nenhum ID obtido.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idVenda;
    }

}
