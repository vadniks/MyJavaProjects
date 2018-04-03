import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
public class SettingsCtrl implements Initializable {
    static CheckBox enablePassword;

    @FXML
    private VBox vBox;

    @FXML
    private Button btExit;

    public SettingsCtrl() {
        vBox = new VBox();
        btExit = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSettings();

        btExit.setOnAction(event -> NotesMain.exit());
    }

    private void initSettings() {
        enablePassword = new CheckBox("Enable password");
        enablePassword.setOnAction(event -> {
            if (enablePassword.isSelected()) {
                File file = new File("serv.txt");
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Vars.vars.isPasswordEnable = true;
                SecurityProcessing.serialize();
                SecurityProcessing2.createWindow();
            } else {
                File file = new File("serv.txt");
                file.delete();
                Vars.vars.isPasswordEnable = false;
                SecurityProcessing.serialize();
            }
        });

        File file = new File("serv.txt");
        if (file.exists()) {
            if (Vars.vars.isPasswordEnable)
                enablePassword.setSelected(true);
        }

        vBox.getChildren().add(enablePassword);
    }
}