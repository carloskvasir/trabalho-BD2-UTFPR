package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class InstallPgDump {

    private static final String PG_VERSION = "16";

    public static void installPgDump() throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            installPgDumpWindows();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            installPgDumpUnix();
        } else {
            System.err.println("Sistema operacional não suportado para a instalação automática.");
        }
    }

    private static void installPgDumpWindows() throws IOException, InterruptedException {
        // URL para download do PostgreSQL no Windows
        String downloadUrl = "https://get.enterprisedb.com/postgresql/postgresql-" + PG_VERSION + "-1-windows-x64-binaries.zip";
        String zipFilePath = System.getProperty("java.io.tmpdir") + "postgresql.zip"; // Diretório temporário
        String destDir = "C:\\Program Files\\PostgreSQL";

        // Comandos PowerShell para download e descompactação
        String[] commands = {
                "powershell", "-Command",
                "& { " +
                        "Invoke-WebRequest -Uri \"" + downloadUrl + "\" -OutFile \"" + zipFilePath + "\"; " +
                        "Expand-Archive -Path \"" + zipFilePath + "\" -DestinationPath \"" + destDir + "\" -Force; " +
                        "}"
        };

        ProcessBuilder pb = new ProcessBuilder(commands);
        executeCommand(pb);

        // Limpeza do arquivo temporário
        new File(zipFilePath).delete();
    }

    private static void installPgDumpUnix() throws IOException, InterruptedException {
        ProcessBuilder pb;

        if (isMac()) {
            // Instalar a versão específica do PostgreSQL no macOS
            pb = new ProcessBuilder("brew", "install", "postgresql@" + PG_VERSION);
            // Adiciona o PATH do PostgreSQL para Homebrew
            System.out.println("Adicionando PostgreSQL ao PATH...");
            String command = "echo 'export PATH=\"/usr/local/opt/postgresql@" + PG_VERSION + "/bin:$PATH\"' >> ~/.zshrc";
            ProcessBuilder addPath = new ProcessBuilder("bash", "-c", command);
            executeCommand(addPath);
        } else {
            // Instalar pg_dump no Linux usando apt-get com versão específica
            pb = new ProcessBuilder("bash", "-c", "sudo apt-get update && sudo apt-get install -y postgresql-client-" + PG_VERSION.replace(".", ""));
        }

        executeCommand(pb);
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
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
            System.out.println("Instalação completada com sucesso.");
        } else {
            System.err.println("Falha na instalação. Código de saída: " + exitCode);
        }
    }
}
