package dao;

import DB_Conection.ConnectionFactory;
import domain.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    public static Funcionario getFuncionarioByNameAndPassword(String user, String password) {
        String query = "SELECT * FROM tb_funcionarios WHERE fun_user LIKE ? AND fun_senha LIKE ?";
        Funcionario funcionario = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + user + "%");
            stmt.setString(2, "%" + password + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                funcionario = Funcionario.builder()
                        .codigo(rs.getLong("fun_codigo"))
                        .nome(rs.getString("fun_nome"))
                        .cpf(rs.getString("fun_cpf"))
                        .senha(rs.getString("fun_senha"))
                        .funcao(rs.getString("fun_funcao"))
                        .user(rs.getString("fun_user"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionario;
    }
}
