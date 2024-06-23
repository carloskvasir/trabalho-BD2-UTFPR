package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class SimplePostgresBackup {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/trabalho_banco";
        String user = "postgres";
        String password = "postgres";

        String backupFile = "/Users/davidpereira/Desktop/github/backup/backup.sql";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            exportDatabaseToSql(conn, backupFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportDatabaseToSql(Connection conn, String backupFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(backupFile))) {
            Statement statement = conn.createStatement();
            ResultSet tables = statement.executeQuery(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema='public'");

            while (tables.next()) {
                String tableName = tables.getString(1);
                exportTableToSql(conn, writer, tableName);
            }

            System.out.println("Backup salvo em: " + backupFile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportTableToSql(Connection conn, PrintWriter writer, String tableName) throws IOException {
        writer.println("-- Exportando tabela: " + tableName);
        writer.println("DROP TABLE IF EXISTS " + tableName + " CASCADE;");
        writer.println("CREATE TABLE " + tableName + " AS SELECT * FROM " + tableName + " WHERE false;");

        try (Statement statement = conn.createStatement()) {
            ResultSet data = statement.executeQuery("SELECT * FROM " + tableName);
            int columnCount = data.getMetaData().getColumnCount();

            while (data.next()) {
                StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName + " VALUES (");

                for (int i = 1; i <= columnCount; i++) {
                    insertStatement.append("'").append(data.getString(i).replace("'", "''")).append("'");
                    if (i < columnCount) {
                        insertStatement.append(", ");
                    }
                }

                insertStatement.append(");");
                writer.println(insertStatement.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        writer.println();
    }
}

