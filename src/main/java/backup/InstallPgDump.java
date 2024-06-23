package backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InstallPgDump {

    public static void main(String[] args) {
        try {
            installPgDump();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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
        String downloadUrl = "https://get.enterprisedb.com/postgresql/postgresql-15.2-1-windows-x64-binaries.zip";

        // Baixar e descompactar usando curl e unzip
        ProcessBuilder pb = new ProcessBuilder(
                "powershell", "-Command",
                String.format(
                        "& {Invoke-WebRequest -Uri \"%s\" -OutFile \"postgresql.zip\"; Expand-Archive -Path \"postgresql.zip\" -DestinationPath \"C:\\Program Files\\PostgreSQL\";}",
                        downloadUrl
                )
        );

        executeCommand(pb);
    }

    private static void installPgDumpUnix() throws IOException, InterruptedException {
        ProcessBuilder pb;

        if (isMac()) {
            // Instalar pg_dump usando Homebrew no macOS
            pb = new ProcessBuilder("brew", "install", "postgresql");
        } else {
            // Instalar pg_dump no Linux usando apt-get
            pb = new ProcessBuilder("bash", "-c", "sudo apt-get update && sudo apt-get install -y postgresql-client");
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
