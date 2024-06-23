package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DatabaseRestore {

    private static final String DB_NAME = "trabalho_banco"; // Nome do banco de dados
    private static final String DB_USER = "postgres"; // Usuário do banco de dados
    private static final String DB_PASSWORD = "postgres"; // Senha do banco de dados
    private static final String DB_HOST = "localhost"; // Host do banco de dados
    private static final String DB_PORT = "5432"; // Porta do banco de dados

    // Detecta o sistema operacional e retorna o caminho para pg_restore
    private static String detectPgRestorePath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_restore.exe"; // Substitua pelo caminho correto
        } else if (os.contains("mac")) {
            return "/usr/local/opt/postgresql@16/bin/pg_restore";
        } else if (os.contains("nix") || os.contains("nux")) {
            return "/usr/lib/postgresql/16/bin/pg_restore";
        }
        return null;
    }

    public static void restoreDatabase(String backupFilePath) throws IOException, InterruptedException {
        File backupFile = new File(backupFilePath);
        if (!backupFile.exists()) {
            throw new IOException("Arquivo de backup não encontrado: " + backupFilePath);
        }

        String pgRestorePath = detectPgRestorePath();
        if (pgRestorePath == null) {
            throw new IOException("Sistema operacional não suportado ou caminho de pg_restore não encontrado.");
        }

        ProcessBuilder pb = new ProcessBuilder(
                pgRestorePath,
                "-h", DB_HOST,
                "-p", DB_PORT,
                "-U", DB_USER,
                "-d", DB_NAME,
                "-v",
                backupFilePath
        );

        pb.environment().put("PGPASSWORD", DB_PASSWORD);

        executeCommand(pb);
    }

    private static void executeCommand(ProcessBuilder pb) throws IOException, InterruptedException {
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Restauração realizada com sucesso.");
        } else {
            throw new IOException("Falha na restauração. Código de saída: " + exitCode);
        }
    }
}
