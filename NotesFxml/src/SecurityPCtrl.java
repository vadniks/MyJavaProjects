import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
public class SecurityPCtrl implements Initializable {
    static boolean canProceed;

    @FXML
    private PasswordField passField;

    @FXML
    private Button btEnter;

    @FXML
    private Label wRLb;

    public SecurityPCtrl() {
        passField = new PasswordField();
        btEnter = new Button();
        wRLb = new Label();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canProceed = false;

        wRLb.setVisible(false);

        btEnter.setOnAction(event -> {
            if (passField.getText().length() >= 4) {
                try {
                    if (SecurityProcessing.decrypt(Vars.vars.password).equals(passField.getText())) {
                        canProceed = true;
                        SecurityProcessing.close();
                        NotesMain.primaryStage.setOpacity(1);
                    } else
                        Animations.shake(passField);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else
                Animations.shake(passField);
        });
    }
}