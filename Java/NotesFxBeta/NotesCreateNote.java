import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates notes.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 14, 2018
 */
public class NotesCreateNote implements IConstants{
    private Stage primaryStage;
    private String newNoteName = "";
    private String newNoteText = "";

    /**
     * Class constructor, creates a window.
     */
    NotesCreateNote() {
        primaryStage = new Stage();
        BorderPane root = new BorderPane();
        primaryStage.setTitle(TITLE_CREATE);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesMain.mainOwner);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        window(root);
        primaryStage.show();
    }

    /**
     *Describes the arrangement of the window,
     * overrides the interface {@code IConstants} method.
     *
     * @param root is a root pane.
     */
    @Override
    public void window(BorderPane root) {
        Label enterNameLb = new Label();
        enterNameLb.setFont(NotesMain.font);
        enterNameLb.setText(ENTER_NAME_LB);
        enterNameLb.setPrefSize(W_BT_DEF-20, H_BT_DEF);

        TextField nameField = new TextField();
        nameField.setPrefSize(W_BT_DEF+20, H_BT_DEF);

        BorderPane namePane = new BorderPane();
        namePane.setPrefSize(WIDTH, H_BT_DEF);
        namePane.setLeft(enterNameLb);
        namePane.setRight(nameField);

        Label enterTextLb = new Label();
        enterTextLb.setFont(NotesMain.font);
        enterTextLb.setText(ENTER_TEXT_LB);

        TextArea textText = new TextArea();
        textText.setWrapText(true);
        textText.setPrefSize(WIDTH, HEIGHT-45);

        BorderPane textPane = new BorderPane();
        textPane.setTop(enterTextLb);
        textPane.setCenter(textText);

        Button btClear = new Button(BT_CLEAR);
        btClear.setPrefSize(W_BT_DEF, H_BT_DEF-10);
        btClear.setOnAction(event -> {
            nameField.clear();
            textText.clear();
        });

        Button btDone = new Button(BT_DONE);
        btDone.setPrefSize(W_BT_DEF, H_BT_DEF-10);
        btDone.setOnAction(event -> {
            if (nameField.getText() != null && !nameField.getText().isEmpty()
                    && textText.getText() != null && !textText.getText().isEmpty()) {
                newNoteName = nameField.getText();
                newNoteText = textText.getText();
                XmlProcessing.createNote(newNoteName, newNoteText);
                NotesMain.mainOwner.hide();
                NotesMain.outerUpdateList();
                NotesMain.isNotAny = false;
                NotesMain.updateLabel();
                primaryStage.close();
                NotesMain.mainOwner.close();
            }
        });

        BorderPane btsPane = new BorderPane();
        btsPane.setLeft(btClear);
        btsPane.setRight(btDone);

        root.setTop(namePane);
        root.setCenter(textPane);
        root.setBottom(btsPane);
    }
}