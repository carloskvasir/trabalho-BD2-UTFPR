package repository;

import DB_Conection.ConnectionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.ItemVenda;
import domain.Venda;

import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class VendaDAO {

    public void inserirVendaComItens(Venda venda, List<ItemVenda> itens) throws SQLException, JsonProcessingException {
        String sql = "CALL inserir_venda_com_itens_proc(?, ?, ?, ?)";
        Connection conn = null;

        try {
            // Estabelecer a conexão
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(true);  // Use auto-commit para permitir que PostgreSQL gerencie a transação

            // Converter a lista de itens para JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String jsonItens = objectMapper.writeValueAsString(itens);

            // Criar um objeto PGobject para JSONB
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(jsonItens);

            // Preparar a chamada da procedure
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setDate(1, Date.valueOf(venda.getHorario()));
                stmt.setBigDecimal(2, BigDecimal.valueOf(venda.getValorTotal()));
                stmt.setLong(3, venda.getFuncionarioCodigo());
                stmt.setObject(4, jsonObject);

                // Executar a procedure
                stmt.execute();
                System.out.println("Venda e itens inseridos com sucesso.");
            }
        } catch (SQLException e) {
            // Capturar e tratar a exceção
            System.err.println("Erro ao inserir venda e itens: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                try {
                    // Fechar a conexão
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
