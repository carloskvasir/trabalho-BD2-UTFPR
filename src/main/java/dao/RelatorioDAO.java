package dao;

import DB_Conection.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {


    public List<String> getRelatorioVendasPeriodo(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        List<String> resultado = new ArrayList<>();
        String sql = "SELECT * FROM relatorio_vendas_periodo(?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(dataInicio));
            pstmt.setDate(2, Date.valueOf(dataFim));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String linha = String.format("Venda: %d | Data: %s | Valor Total: %.2f | Código Func.: %d | Nome Func.: %s",
                            rs.getLong("ven_codigo"),
                            rs.getDate("Data da Venda").toString(),
                            rs.getBigDecimal("Valor Total da Venda"),
                            rs.getLong("Codigo do Funcionario"),
                            rs.getString("Nome do Funcionario"));
                    resultado.add(linha);
                }
            }

        }

        return resultado;
    }


}

