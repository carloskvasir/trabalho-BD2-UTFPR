package DB_Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    public ConnectionFactory() {
    }

    //TODO criar getConnection com parametros de usuário e senha
    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/trabalho_banco_2";
        String user = "postgres";
        String password = "postgres";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
