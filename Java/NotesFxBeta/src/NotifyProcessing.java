import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates notifications for notes.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 24, 2018
 */
public class NotifyProcessing implements IConstants {
    private int newValueD;
    private int newValueM;
    private Stage primaryStage;
    private String nameT;
    private String textT;
    private static TrayIcon trayIcon;
    private int newValueH;
    private int newValueMi;

    /**
     * Class constructor, creates a window.
     */
    NotifyProcessing(String name, String text) {
        nameT = name;
        textT = text;
        primaryStage = new Stage();
        BorderPane root = new BorderPane();
        primaryStage.setTitle(TITLE_NOTIFY);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesCreateNote.primaryStage);
        primaryStage.setScene(new Scene(root, WIDTH, H_BT_DEF*5));
        window(root);
        primaryStage.show();
    }

    /**
     * Describes the arrangement of the window,
     * overrides the interface {@code IConstants} method.
     *
     * @param root is a root pane.
     */
    @Override
    public void window(BorderPane root) {
        Label setDtLb = new Label();
        setDtLb.setText(SET_DATE_LB);
        setDtLb.setFont(NotesMain.font);

        ObservableList<Integer> days = FXCollections.observableArrayList();
        days.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);

        ObservableList<Integer> months = FXCollections.observableArrayList();
        months.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 1; i < 24; i++) {
            hours.add(i);
        }

        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            minutes.add(i);
        }

        ChoiceBox<Integer> day = new ChoiceBox<>();
        day.setItems(days);

        ChoiceBox<Integer> month = new ChoiceBox<>();
        month.setItems(months);

        ChoiceBox<Integer> hour = new ChoiceBox<>();
        hour.setItems(hours);

        ChoiceBox<Integer> minute = new ChoiceBox<>();
        minute.setItems(minutes);

        SingleSelectionModel<Integer> daySelModel = day.getSelectionModel();
        daySelModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> newValueD = newValue);

        SingleSelectionModel<Integer> monthSelModel = month.getSelectionModel();
        monthSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueM = newValue));

        SingleSelectionModel<Integer> hourSelModel = hour.getSelectionModel();
        hourSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueH = newValue));

        SingleSelectionModel<Integer> minuteSelModel = minute.getSelectionModel();
        minuteSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueMi = newValue));

        Button btDone = new Button(BT_DONE);
        btDone.setPrefSize(WIDTH, H_BT_DEF);
        btDone.setOnAction(event -> {
            if (newValueD != 0 && newValueM != 0) {
                try {
                    createNotify();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();
            }
        });

        BorderPane datePane = new BorderPane();
        datePane.setMaxSize(WIDTH, H_BT_DEF*3);
        datePane.setLeft(day);
        datePane.setRight(month);

        BorderPane timePane = new BorderPane();
        timePane.setLeft(hour);
        timePane.setRight(minute);

        BorderPane dateTimePane = new BorderPane();
        dateTimePane.setLeft(datePane);
        dateTimePane.setRight(timePane);

        root.setTop(setDtLb);
        root.setCenter(dateTimePane);
        root.setBottom(btDone);
    }


    /**
     * Creates a reminder by means of notification.
     *
     * @throws ParseException just not to handle this exception.
     */
    private void createNotify() throws ParseException {
        //XmlProcessing.createDate(date, XmlProcessing.getCount()); // <-- For the future.

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, newValueD);
        if (newValueM == 1)
            calendar.set(Calendar.MONTH, 0);
        else calendar.set(Calendar.MONTH, newValueM-1);
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.HOUR_OF_DAY, newValueH);
        calendar.set(Calendar.MINUTE, newValueMi);
        calendar.set(Calendar.SECOND, 0);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                trayIcon.displayMessage("Notify: " + nameT, textT, TrayIcon.MessageType.INFO);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, calendar.getTime());
    }


    /**
     * Makes this program work with system tray.
     */
    static void tray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(NotesMain.class.getResource("notes.png"));

            ActionListener exitListener = e -> System.exit(0);
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "Notes", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        super.mouseClicked(e);
                        Platform.runLater(() -> NotesMain.mainOwner.show());
                    }
                }
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }
    }
}