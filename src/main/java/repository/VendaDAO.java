package repository;

import DB_Conection.ConnectionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Funcionario;
import domain.ItemVenda;
import domain.Venda;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

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

    public void inserirVendaComItens(Venda venda, List<ItemVenda> itens) {
        String sql = "SELECT inserir_venda_com_itens(?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preparar os parâmetros
            stmt.setTimestamp(1, Timestamp.valueOf(venda.getHorario().atStartOfDay())); // Converte LocalDate para Timestamp
            stmt.setBigDecimal(2, BigDecimal.valueOf(venda.getValorTotal())); // Use BigDecimal para precisão
            stmt.setLong(3, venda.getFuncionarioCodigo());

            // Converter itens para JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String jsonItens = objectMapper.writeValueAsString(itens);
            stmt.setObject(4, jsonItens, java.sql.Types.OTHER); // Use java.sql.Types.OTHER para JSONB

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getLong(1) != 0) {
                    System.out.println("Venda e itens inseridos com sucesso.");
                } else {
                    System.out.println("Falha ao inserir venda e itens.");
                }
            }

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
