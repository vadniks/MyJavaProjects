import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Apr 2, 2018.
 */
public class SecurityP2Ctrl implements Initializable {

    @FXML
    private PasswordField passField;

    @FXML
    private Button btEnter;

    @FXML
    private Label wRLb;

    public SecurityP2Ctrl() {
        passField = new PasswordField();
        btEnter = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wRLb.setVisible(false);

        btEnter.setOnAction(event -> {
            if (passField.getText().length() >= 4) {
                try {
                    Vars.vars.password = SecurityProcessing.encrypt(passField.getText());
                    NotesMain.exit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Animations.shake(passField);
                wRLb.setVisible(true);
            }
        });
    }
}