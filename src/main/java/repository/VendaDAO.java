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

    public void inserirVendaComItens(Venda venda, List<ItemVenda> itens) throws SQLException, JsonProcessingException {
        String sql = "SELECT inserir_venda_com_itens(?, ?, ?, ?)";
        Connection conn = null;

        try {
            // Estabelecer a conexão e iniciar a transação
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);  // Desativar auto-commit

            // Inserir venda e itens
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Preparar os parâmetros para a função PL/pgSQL
                stmt.setTimestamp(1, Timestamp.valueOf(venda.getHorario().atStartOfDay()));
                stmt.setBigDecimal(2, BigDecimal.valueOf(venda.getValorTotal()));
                stmt.setLong(3, venda.getFuncionarioCodigo());

                // Converter a lista de itens para JSON
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
                            throw new SQLException("Falha ao inserir venda e itens.");
                        }
                    }
                }
            }

        } catch (SQLException | JsonProcessingException e) {
            if (conn != null) {
                try {
                    // Rollback em caso de erro
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;  // Relançar a exceção
        }
    }
}