package view;

import backup.DatabaseBackup;
import backup.DatabaseRestore;
import backup.InstallPgDump;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import service.Funcao3a;
import service.FuncionarioService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainScreen {
    public void onNavigateToVenda(ActionEvent event) {
        try {
            NavigationUtil.navigateToScreen(event, "vendaScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNavigateToRelatorio(ActionEvent event) {
        try {
            NavigationUtil.navigateToScreen(event, "relatorio.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onBackup() {
        // Instância do DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar Diretório de Backup");

        // Padrão inicial (opcional)
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Mostrar o diálogo e capturar o diretório selecionado
        File selectedDirectory = directoryChooser.showDialog(null); // Usar null se não houver referência ao Stage

        if (selectedDirectory != null) {
            System.out.println("Diretório selecionado: " + selectedDirectory.getAbsolutePath());
            try {
                InstallPgDump.installPgDump();
                DatabaseBackup.backupDatabase(selectedDirectory.getAbsolutePath());
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Backup realizado com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao realizar o backup: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum diretório selecionado");
        }
    }

    @FXML
    private void onRestaurar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo de Backup");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de Backup", "*.backup"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());
            try {
                DatabaseRestore.restoreDatabase(selectedFile.getAbsolutePath());
                FuncionarioService.setFuncionario();
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Restauração realizada com sucesso.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao restaurar o backup: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum arquivo selecionado");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onRollback(ActionEvent event) {
        try {
            Funcao3a.callTentarInserirComRollback(999929876L,"test","122323423","test", "test", "test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
