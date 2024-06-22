package DB_Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/trabalho_banco";

    public static Connection getConnection() throws SQLException {
        CurrentUser currentUser = CurrentUser.getInstance();
        String user = currentUser.getUsername();
        String password = currentUser.getPassword();
        return DriverManager.getConnection(URL, user, password);
    }
}
