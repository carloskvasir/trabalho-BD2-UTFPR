package trabalho_banco_2.trabalhobd2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MainScreen {

    @FXML
    private Button button_ok;

    @FXML
    private Label messageLabel;


    @FXML
    private void hanlerOnClick(){
        messageLabel.setText("clicado");
    }

}
