import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The main class of the program
 * describing params that are common for all classes.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 14, 2018
 */
public class NotesMain extends Application implements IConstants {
    static Stage mainOwner;
    static Font font;
    static ArrayList<String> notesNames = new ArrayList<>();
    static String userPath = System.getProperty("user.home");
    static boolean isNotAny;
    private static ObservableList<String> listData;
    private static Label notAnyLb;
    static HashMap<String, String> hashMap = new HashMap<>();
    private static ListView<String> list;

    /**
     *Describes the arrangement of the window,
     * overrides the interface {@code IConstants} method.
     *
     * @param primaryStage is JavaFx component that is needed to be overrided.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        updateList();
        BorderPane root = new BorderPane();
        primaryStage.setTitle(TITLE_MAIN);
        primaryStage.setResizable(false);
        mainOwner = primaryStage;
        NotesMain.mainOwner.getIcons().add(new Image(NotesMain.class.getResourceAsStream("notes.png")));
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        window(root);
        primaryStage.show();
        Platform.setImplicitExit(false);
        NotifyProcessing.tray();
        primaryStage.setOnCloseRequest(event -> NotifyProcessing.trayIcon.displayMessage("Notes app minimized to tray.",
                "Notes app continues working in the system tray.", TrayIcon.MessageType.INFO));
    }

    /**
     *Describes the arrangement of the window,
     * overrides the interface {@code IConstants} method.
     *
     * @param root is a root pane.
     */
    @Override
    public void window(BorderPane root) {
        Label label = new Label(LABEL);
        label.setPrefSize(WIDTH/2, H_BT_DEF);
        font = new Font(25);
        label.setFont(font);

        notAnyLb = new Label();
        notAnyLb.setText(NOT_ANY_LB);

        BorderPane lbPane = new BorderPane();
        lbPane.setLeft(label);
        if (isNotAny)
            lbPane.setRight(notAnyLb);

        listData = FXCollections.observableArrayList();
        listData.addAll(notesNames);

        list = new ListView<>(listData);
        list.setEditable(false);

        MultipleSelectionModel<String> listSelModel = list.getSelectionModel();
        listSelModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> new NotesNoteView(newValue));
        listSelModel.selectedIndexProperty().addListener((observable, oldValue, newValue) -> NotesNoteView.delValue = (int) newValue);
        listSelModel.selectedIndexProperty().addListener((observable, oldValue, newValue) -> NotesNoteView.editValue = (int) newValue);

        Button btCreate = new Button();
        btCreate.setText(BT_CREATE);
        btCreate.setPrefSize(W_BT_DEF, H_BT_DEF);
        btCreate.setOnAction(event -> new NotesCreateNote());

        Button btClear = new Button();
        btClear.setText(BT_CLEAR_LIST);
        btClear.setPrefSize(W_BT_DEF, H_BT_DEF);
        btClear.setOnAction(event -> {
            if (!notesNames.isEmpty() && listSelModel.getSelectedItem() != null) {
                listSelModel.clearSelection();
                NotesNoteView.close();
            }
        });

        BorderPane btsPane = new BorderPane();
        btsPane.setPrefSize(WIDTH, H_BT_DEF);
        btsPane.setLeft(btCreate);
        btsPane.setRight(btClear);

        root.setTop(lbPane);
        root.setCenter(list);
        root.setBottom(btsPane);
    }

    /**
     * Updates the label when note was created {@code notAnyLb}.
     */
    static void updateLabel() {
        notAnyLb.setText("");
    }

    /**
     * Updates the label when note was deleted {@code notAnyLb}.
     */
    static void updateLabel2() {
        notAnyLb.setText(NOT_ANY_LB);
    }

    /**
     * Updates the array list {@code listData} from outside off the class.
     */
    static void outerUpdateList() {
        listData.clear();
        listData.addAll(notesNames);
        list.setItems(listData);
    }

    /**
     * Main method updates array list {@code listData} by reading xml using method readXml {@code readXml()} file that contains notes.
     */
    static void updateList() {
        isNotAny = false;
        HashMap map = XmlProcessing.readXml();
        Set<Map.Entry<String, String>> set = map.entrySet();
        notesNames.clear();
        String newListData = null;
        for (Map.Entry<String, String> o : set)
            newListData += o.getValue();

        if (newListData != null && newListData.contains("null"))
                    newListData = newListData.substring(4);

        if (newListData != null) {
            String[] arrData = newListData.split("~");
            int indexRegex;
            int afterIR;
            for (String anArrData : arrData) {
                indexRegex = anArrData.indexOf(":");
                notesNames.add(anArrData.substring(0, indexRegex));
                afterIR = anArrData.length();
                hashMap.put(anArrData.substring(0, indexRegex), anArrData.substring(indexRegex+1, afterIR));
            }
        } else isNotAny = true;
    }

    /**
     * Main 'psvm' method that executes the program,
     * call to the launch method {@code launch(args)} that launches the JavaFx application.
     *
     * @param args is an array that contains symbols from the commandline if input is needed.
     */
    public static void main(String[] args) {
        new XmlProcessing();
        if (XmlProcessing.canRun()) {
            XmlProcessing.setRunFlagToFalse();
            checkExiting();
            launch(args);
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "You can't run this application twice!",
                    "Notes",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Checks the exiting flag (is program needs to be exited).
     */
    private static void checkExiting() {
        Thread main = Thread.currentThread();

        new Thread(() -> {
            while (main.isAlive()) {
                if (NotifyProcessing.isExiting)
                    XmlProcessing.setRunFlagToTrue();
            }
        }).start();
    }
}