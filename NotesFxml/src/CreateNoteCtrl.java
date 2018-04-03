import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Mar 25, 2018.
 */
public class CreateNoteCtrl implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btClear;

    @FXML
    private Button btNotify;

    @FXML
    private Button btDone;

    public CreateNoteCtrl() {
        textField = new TextField();
        textArea = new TextArea();
        btClear = new Button();
        btNotify = new Button();
        btDone = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTexts();
        initButtons();
    }

    private void initTexts() {
        final int LIMIT = 40;
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText().length() > LIMIT)
                textField.setText(textField.getText().substring(0, LIMIT));
        });

        textArea.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (textField.getText().isEmpty() || textField.getText().equals(oldValue)) {
                if (textField.getText().length() < LIMIT) {
                    if (oldValue.length() < newValue.length())
                        textField.appendText(textArea.getText().substring(oldValue.length()));
                    else
                        textField.deletePreviousChar();
                }
                else {
                    if (textField.getText().length() > LIMIT)
                        textField.setText(textField.getText().substring(0, LIMIT));
                }

                if (textArea.getText().length() == 0 && textArea.getCaretPosition() == 1)
                    textField.clear();
            }
        }));

        if (textArea.getText().length() > 0)
            textField.setText(textArea.getText().substring(0, LIMIT));

        textArea.setWrapText(true);
        textArea.positionCaret(1);
    }

    private void initButtons() {
        btClear.setOnAction(event -> {
            textField.clear();
            textArea.clear();
        });

        btNotify.setOnAction(event -> {
            boolean isAlrdExists = false;

            for (String o : HibernateProcessing.getNames()) {
                if (o.split("\t")[1].equals(textField.getText()))
                    isAlrdExists = true;
            }

            if (!isAlrdExists && (textField.getText().length() != 0 && textArea.getText().length() != 0))
                Vars.np = new NotifyProcessing(textField.getText(), textArea.getText());
            else {
                Animations.shake(textField);
                Animations.shake(textArea);
            }
        });

        btDone.setOnAction(event -> {
            if (textField.getText().length() != 0 && textArea.getText().length() != 0) {
                boolean isAlrdExists = false;

                for (String o : HibernateProcessing.getNames()) {
                    if (o.split("\t")[1].equals(textField.getText()))
                        isAlrdExists = true;
                }

                if (HibernateProcessing.getNames().size() > 0) {
                    if (!isAlrdExists) {
                        HibernateProcessing.insert(textField.getText(), textArea.getText());
                        MainCtrl.updateList();
                        NotesCreateNote.close();
                    } else
                        Animations.shake(textField);
                } else {
                    HibernateProcessing.initTable(textField.getText(), textArea.getText());
                    MainCtrl.updateList();
                    NotesCreateNote.close();
                }
            } else {
                Animations.shake(textField);
                Animations.shake(textArea);
            }
        });
    }

    @NotUsed
    private void showMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Notes with this name already exists!");
        alert.setTitle("Error");
        alert.initOwner(NotesCreateNote.primaryStage);
        alert.setResizable(false);
        alert.setOnShowing(event1 -> Toolkit.getDefaultToolkit().beep());
    }
}