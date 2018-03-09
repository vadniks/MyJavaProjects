import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Class that is used to view the content of each note.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 16, 2018
 */
public class NotesNoteView implements IConstants{
    private String nameFinal;
    private static Stage primaryStage;
    private String newValue;
    static int delValue;
    private TextArea textArea;
    static int editValue;

    /**
     * Class constructor, creates a window.
     *
     * @param newValue is a value of the chosen item in the NotesMain class list view {@code listView}
     *                 that contains name of the chosen note.
     */
    NotesNoteView(String newValue) {
        this.newValue = newValue;
        this.nameFinal = TITLE_VIEW + newValue;
        primaryStage = new Stage();
        BorderPane root = new BorderPane();
        primaryStage.setTitle(nameFinal);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesMain.mainOwner);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        window(root);
        primaryStage.show();
    }

    /**
     * Method is used to close the window {@code primaryStage}.
     */
    static void close() {
        primaryStage.close();
    }

    /**
     * Describes the arrangement of the window,
     * overrides the interface {@code IConstants} method.
     *
     * @param root is a root pane.
     */
    @Override
    public void window(BorderPane root) {
        Label nameLb = new Label();
        nameLb.setFont(NotesMain.font);
        nameLb.setText(nameFinal);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(true);
        textArea.setText(NotesMain.hashMap.get(newValue));

        Button btDelete = new Button(BT_DELETE);
        btDelete.setPrefSize(W_BT_DEF, H_BT_DEF);
        btDelete.setOnAction(event -> {
            NotesMain.notesNames.remove(newValue);
            XmlProcessing.resetCount();
            XmlProcessing.deleteNote(delValue+XmlProcessing.getCount());
            NotesMain.mainOwner.close();
            NotesMain.updateList();
            NotesMain.outerUpdateList();
            if (NotesMain.notesNames.isEmpty())
                NotesMain.updateLabel2();
            close();
            NotesMain.mainOwner.show();
        });

        Button btEdit = new Button(BT_EDIT);
        btEdit.setPrefSize(W_BT_DEF, H_BT_DEF);
        btEdit.setOnAction(event -> {
            if (textArea.getText() != null && !textArea.getText().isEmpty()) {
                XmlProcessing.resetCount();
                XmlProcessing.editNote(newValue , textArea.getText(), editValue+XmlProcessing.getCount());
                NotesMain.mainOwner.close();
                NotesMain.updateList();
                NotesMain.outerUpdateList();
                close();
                NotesMain.mainOwner.show();
            } else Toolkit.getDefaultToolkit().beep();
        });

        BorderPane btsPane = new BorderPane();
        btsPane.setPrefSize(WIDTH, H_BT_DEF);
        btsPane.setLeft(btDelete);
        btsPane.setRight(btEdit);

        root.setTop(nameLb);
        root.setCenter(textArea);
        root.setBottom(btsPane);
    }
}