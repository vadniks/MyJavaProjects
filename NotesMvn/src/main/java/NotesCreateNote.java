import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Creates notes.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 14, 2018
 */
public class NotesCreateNote implements IConstants{
    static Stage primaryStage;
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
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
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
        TextField nameField = new TextField();
        nameField.setPrefSize(WIDTH, H_BT_DEF);
        nameField.setPromptText("Name of note");
        nameField.setTooltip(new Tooltip("name of note"));

        BorderPane namePane = new BorderPane();
        namePane.setPrefSize(WIDTH, H_BT_DEF);
        namePane.setRight(nameField);

        TextArea textText = new TextArea();
        textText.setWrapText(true);
        textText.setPrefSize(WIDTH, HEIGHT-45);
        textText.setPromptText("Text of note");
        textText.setTooltip(new Tooltip("text of note"));

        BorderPane textPane = new BorderPane();
        textPane.setCenter(textText);

        Button btClear = new Button(BT_CLEAR);
        btClear.setPrefSize(W_BT_3, H_BT_DEF-10);
        btClear.setOnAction(event -> {
            nameField.clear();
            textText.clear();
        });

        Button btDone = new Button(BT_DONE);
        btDone.setPrefSize(W_BT_3, H_BT_DEF-10);
        btDone.setOnAction(event -> {
            if (nameField.getText() != null && !nameField.getText().isEmpty()
                    && textText.getText() != null && !textText.getText().isEmpty()) {
                newNoteName = nameField.getText();
                newNoteText = textText.getText();
                XmlProcessing.createNote(newNoteName, newNoteText);
                NotesMain.mainOwner.hide();
                NotesMain.isNotAny = false;
                NotesMain.updateLabel();
                NotesMain.outerUpdateList();
                primaryStage.close();
                NotesMain.mainOwner.close();
                NotesMain.updateList();
                NotesMain.outerUpdateList();
                NotesMain.mainOwner.show();
            } else Toolkit.getDefaultToolkit().beep();
        });

        Button btDate = new Button(BT_DATE);
        btDate.setPrefSize(W_BT_3, H_BT_DEF-10);
        btDate.setOnAction(event -> {
            if (nameField.getText() != null && !nameField.getText().isEmpty() &&
                    textText.getText() != null && !textText.getText().isEmpty())
                new NotifyProcessing(nameField.getText(), textText.getText());
            else Toolkit.getDefaultToolkit().beep();
        });

        BorderPane btsPane = new BorderPane();
        btsPane.setLeft(btClear);
        btsPane.setRight(btDone);
        btsPane.setCenter(btDate);

        root.setTop(namePane);
        root.setCenter(textPane);
        root.setBottom(btsPane);
    }
}