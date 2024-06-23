package view;

import backup.DatabaseBackup;
import backup.InstallPgDump;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import service.Funcao3a;

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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onRollback(MouseEvent event) {
        try {
            Funcao3a.callTentarInserirComRollback(999929876L,"test","122323423","test", "test", "test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
