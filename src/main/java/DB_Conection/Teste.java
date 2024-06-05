package DB_Conection;

import java.sql.SQLException;

public class Teste {

    public static void main(String[] args) {
        try {
            System.out.println(ConnectionFactory.getConnection("postgres", "postgres"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
