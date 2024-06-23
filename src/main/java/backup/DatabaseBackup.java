package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseBackup {

    private static final String DB_NAME = "trabalho_banco"; // Nome do banco de dados
    private static final String DB_USER = "postgres"; // Usuário do banco de dados
    private static final String DB_PASSWORD = "postgres"; // Senha do banco de dados
    private static final String DB_HOST = "localhost"; // Host do banco de dados
    private static final String DB_PORT = "5432"; // Porta do banco de dados
    private static final String BACKUP_DIR = "/Users/davidpereira/Desktop/github/backup"; // Diretório de backup

    public static void main(String[] args) {
        try {
            backupDatabase();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void backupDatabase() throws IOException, InterruptedException {
        // Verifica e cria o diretório de backup se não existir
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            if (!backupDir.mkdirs()) {
                System.err.println("Erro: Não foi possível criar o diretório de backup: " + backupDir.getAbsolutePath());
                return;
            }
        }

        // Gera o nome do arquivo de backup com base na data e hora atuais
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupFileName = DB_NAME + "_" + timestamp + ".backup";
        String backupFilePath = BACKUP_DIR + File.separator + backupFileName;

        // Detecta o sistema operacional e define o caminho para pg_dump
        String pgDumpPath = detectPgDumpPath();
        if (pgDumpPath == null) {
            System.err.println("Erro: Sistema operacional não suportado para a instalação automática.");
            return;
        }

        // Executa o comando de backup
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

    private static String detectPgDumpPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_dump.exe"; // Substitua pelo caminho correto se necessário
        } else if (os.contains("mac")) {
            return "/usr/local/opt/postgresql@16/bin/pg_dump";
        } else if (os.contains("nix") || os.contains("nux")) {
            return "/usr/lib/postgresql/16/bin/pg_dump";
        }
        return null;
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
            System.err.println("Falha no backup. Código de saída: " + exitCode);
        }
    }
}
