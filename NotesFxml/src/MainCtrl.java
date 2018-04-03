import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Vad Nik.
 * @version dated Mar 25, 2018.
 */
public class MainCtrl implements Initializable {
    private static ArrayList<String> arr = new ArrayList<>();
    private static ObservableList<String> observableList;
    static MultipleSelectionModel selModel;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button btCreate;

    @FXML
    private Button btSettings;

    @FXML
    private Button btClear;

    @FXML
    private Hyperlink href;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Button btDelAll;

    @FXML
    private Button btExit;

    public MainCtrl() {
        listView = new ListView<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new HibernateProcessing();
        try {
            SecurityProcessing.decryptDb();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        arr.addAll(HibernateProcessing.getNamesForList());

        observableList = listView.getItems();
        observableList.addAll(arr);

        selModel = listView.getSelectionModel();
        try {
            selModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (selModel.getSelectedIndex() != -1)
                    NotesViewNote.createWindow((String) newValue, HibernateProcessing.getTextByName((String) newValue));
            });
        } catch (Exception ignored) { }
        selModel.selectedIndexProperty().addListener(((observable, oldValue, newValue) -> NotesViewNote.item = (int) newValue));

        btCreate.setOnAction(event -> NotesCreateNote.createWindow());

        btSettings.setOnAction(event -> {
            NotesSettings.createWindow();
        });

        btClear.setOnAction(event -> {
            try {
                selModel.clearSelection();
            } catch (Exception ignored) { }
        });
        btClear.setVisible(false);

        href.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("What is 'Notes' app?");
            alert.setContentText("'Notes' is the application that allows you to create, view, edit, delete and set notification" +
                    "for your notes as a reminder.\nAuthor Vad Nik, https://github.com/vadniks coded on Java(TM).");
            alert.setTitle("About");
            alert.initOwner(NotesCreateNote.primaryStage);
            alert.setResizable(false);
            alert.initOwner(NotesMain.primaryStage);
            alert.show();
        });

        checkbox.setVisible(false);

        btDelAll.setOnAction(event -> {
            if (!listView.getItems().isEmpty()) {
                HibernateProcessing.deleteAll();
                updateList();
            }
        });

        btExit.setOnAction(event -> NotesMain.exit());
    }

    @NotUsed
    private static void sortListByDate() {
        List<Integer> x = HibernateProcessing.getIds();
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for(int i=1; i < x.size(); i++) {
                int temp = 0;
                if(x.get(i-1) > x.get(i)) {
                    temp = x.get(i-1);
                    x.add(i-1, x.get(i));
                    x.add(i, temp);
                    swapped = true;
                }
            }
        }
        updateList(x);
    }

    @NotUsed
    private boolean isSortedByDate() {
        for (Integer o : HibernateProcessing.getIds()) {
            for (String l : arr) {
                if (o != Integer.parseInt(l))
                    return true;
            }
        }
        return false;
    }

    static void updateList() {
        observableList.removeAll(arr);
        arr.clear();
        arr.addAll(HibernateProcessing.getNamesForList());
        observableList.addAll(arr);
    }

    private static void updateList(List<Integer> array) {
        observableList.removeAll(arr);
        arr.clear();
        for (Integer o : array)
            arr.add(String.valueOf(o));
        for (Integer o : array)
            observableList.add(String.valueOf(o));
    }
}