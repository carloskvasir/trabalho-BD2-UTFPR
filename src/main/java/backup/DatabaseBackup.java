package backup;

import DB_Conection.CurrentUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseBackup {

    private static final String DB_NAME = "trabalho_banco";
    private static final String DB_USER = CurrentUser.getInstance().getUsername();
    private static final String DB_PASSWORD = CurrentUser.getInstance().getPassword();
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "5432";

    private static String detectPgDumpPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_dump.exe";
        } else if (os.contains("mac")) {
            return "/usr/local/opt/postgresql@16/bin/pg_dump";
        } else if (os.contains("nix") || os.contains("nux")) {
            return "/usr/lib/postgresql/16/bin/pg_dump";
        }
        return null;
    }

    public static void backupDatabase(String backupDir) throws IOException, InterruptedException {
        File backupDirectory = new File(backupDir);
        if (!backupDirectory.exists()) {
            if (!backupDirectory.mkdirs()) {
                throw new IOException("Não foi possível criar o diretório de backup: " + backupDirectory.getAbsolutePath());
            }
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupFileName = DB_NAME + "_" + timestamp + ".backup";
        String backupFilePath = backupDirectory.getAbsolutePath() + File.separator + backupFileName;

        String pgDumpPath = detectPgDumpPath();
        if (pgDumpPath == null) {
            throw new IOException("Sistema operacional não suportado ou caminho de pg_dump não encontrado.");
        }

        ProcessBuilder pb = new ProcessBuilder(
                pgDumpPath,
                "-h", DB_HOST,
                "-p", DB_PORT,
                "-U", DB_USER,
                "-F", "c",
                "-b",
                "-v",
                "-f", backupFilePath,
                DB_NAME
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
            System.out.println("Backup realizado com sucesso.");
        } else {
            throw new IOException("Falha no backup. Código de saída: " + exitCode);
        }
    }
}