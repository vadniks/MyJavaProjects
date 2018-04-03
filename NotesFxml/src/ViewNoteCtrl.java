import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Mar 27, 2018
 */
public class ViewNoteCtrl implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btExit;

    @FXML
    private Button btEdit;

    @FXML
    private Button btDelete;

    @FXML
    private Button btClear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTexts();

        textField.setText(NotesViewNote.tempName);
        textArea.setText(NotesViewNote.tempText);

        btExit.setOnAction(event -> NotesMain.exit());

        btEdit.setOnAction(event -> {
            if ((textField.getText().length() != 0 && textArea.getText().length() != 0) &&
                    !(NotesViewNote.tempName.equals(textField.getText()) && NotesViewNote.tempText.equals(textArea.getText()))) {
                HibernateProcessing.setNameById(NotesViewNote.item+1, textField.getText());
                HibernateProcessing.setTextByName(textField.getText(), textArea.getText());
                MainCtrl.updateList();
                NotesViewNote.close();
            } else if(textField.getText().length() != 0 && textArea.getText().length() != 0) {
                Animations.shake(textField);
                Animations.shake(textArea);
            }
        });

        btDelete.setOnAction(event -> {
            if (textField.getText().length() != 0 && textArea.getText().length() != 0) {
                HibernateProcessing.delete(NotesViewNote.tempName);
                MainCtrl.updateList();
                NotesViewNote.close();
            } else {
                Animations.shake(textField);
                Animations.shake(textArea);
            }
        });

        btClear.setOnAction(event -> {
            textField.clear();
            textArea.clear();
        });
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
}