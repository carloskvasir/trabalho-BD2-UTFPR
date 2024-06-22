package repository;

import DB_Conection.ConnectionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.ItemVenda;
import domain.Venda;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class VendaDAO {

    public void inserirVendaComItens(Venda venda, List<ItemVenda> itens) {
        String sqlAjustarSequencia = "SELECT setval(pg_get_serial_sequence('tb_vendas', 'ven_codigo'), COALESCE(MAX(ven_codigo) + 1, 1), false) FROM tb_vendas";
        String sqlInserirVendaComItens = "SELECT inserir_venda_com_itens(?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Ajustar a sequência
            try (Statement stmtAjustar = conn.createStatement()) {
                stmtAjustar.executeQuery(sqlAjustarSequencia);
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInserirVendaComItens)) {
                stmt.setTimestamp(1, Timestamp.valueOf(venda.getHorario().atStartOfDay()));
                stmt.setBigDecimal(2, BigDecimal.valueOf(venda.getValorTotal()));
                stmt.setLong(3, venda.getFuncionarioCodigo());

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                String jsonItens = objectMapper.writeValueAsString(itens);
                stmt.setObject(4, jsonItens, java.sql.Types.OTHER);

                // Executar a função SQL
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long idVenda = rs.getLong(1);
                        if (idVenda != null) {
                            System.out.println("Venda e itens inseridos com sucesso. ID da Venda: " + idVenda);
                            conn.commit();  // Commitar a transação
                        } else {
                            System.out.println("Falha ao inserir venda e itens.");
                            conn.rollback();  // Rollback em caso de falha
                        }
                    }
                }
            }

        } catch (SQLException | JsonProcessingException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
}
