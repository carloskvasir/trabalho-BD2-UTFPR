package DB_Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    public ConnectionFactory() {
    }

    //TODO criar getConnection com parametros de usuário e senha
    public static Connection getConnection(String user, String password) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho_banco";

        return DriverManager.getConnection(url, user, password);
    }
}
