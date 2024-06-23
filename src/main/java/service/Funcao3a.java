package service;

import DB_Conection.ConnectionFactory;
import view.AlertaUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class Funcao3a {

    public static void callTentarInserirComRollback(
            long funCodigo, String funUser, String funNome,
            String funCpf, String funSenha, String funFuncao
    ) throws SQLException {

        try (Connection conn = ConnectionFactory.getConnection()) {

            String sql = "{ call tentar_inserir_com_rollback(?, ?, ?, ?, ?, ?) }";
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setLong(1, funCodigo);
                stmt.setString(2, funUser);
                stmt.setString(3, funNome);
                stmt.setString(4, funCpf);
                stmt.setString(5, funSenha);
                stmt.setString(6, funFuncao);

                stmt.execute();
                SQLWarning warning = stmt.getWarnings();
                while (warning != null) {
                    AlertaUtil.mostrarAlertaInformacao("Aviso/Notificação", null, warning.getMessage());
                    warning = warning.getNextWarning();
                }
            } catch (SQLException e) {
                AlertaUtil.mostrarAlertaErro("Erro ao chamar a função", e.getMessage());
                throw e;
            }
        }
    }
}