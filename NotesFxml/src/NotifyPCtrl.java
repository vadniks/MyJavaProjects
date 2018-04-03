import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Mar 31, 2018.
 */
public class NotifyPCtrl implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textField;

    @FXML
    private Button btDone;

    public NotifyPCtrl() {
        datePicker = new DatePicker();
        textField = new TextField();
        btDone = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setOnAction(event -> {
            LocalDate lcDate = datePicker.getValue();
            if (lcDate.toString().matches("([2][0]\\d\\d[-][0-1]\\d[-][0-3]\\d)")) {
                NotifyProcessing.newValueD = lcDate.getDayOfMonth();
                NotifyProcessing.day = true;
                NotifyProcessing.newValueM = lcDate.getMonth().getValue();
                NotifyProcessing.month = true;
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("([0-23]\\d[-][0-59]\\d)")) {
                NotifyProcessing.newValueH = Integer.parseInt(newValue.split("-")[0]);
                NotifyProcessing.hour = true;
                NotifyProcessing.newValueMi = Integer.parseInt(newValue.split("-")[1]);
                NotifyProcessing.minute = true;
            }
        });

        btDone.setOnAction(event -> {
            if (NotifyProcessing.day && NotifyProcessing.month && NotifyProcessing.hour && NotifyProcessing.minute) {
                try {
                    Vars.np.createNotify();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                NotifyProcessing.primaryStage.close();
            } else {
                Animations.shake(datePicker);
                Animations.shake(textField);
            }
        });
    }
}