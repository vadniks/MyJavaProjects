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
    static TrayIcon trayIcon;
    private int newValueH;
    private int newValueMi;
    static boolean isExiting;

    /**
     * Class constructor, creates a window.
     */
    NotifyProcessing(String name, String text) {
        isExiting = false;

        nameT = name;
        textT = text;
        primaryStage = new Stage();
        BorderPane root = new BorderPane();
        primaryStage.setTitle(TITLE_NOTIFY);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesCreateNote.primaryStage);
        primaryStage.setScene(new Scene(root));
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
        Label dayLb = new Label();
        dayLb.setText(DAY_LB);

        Label monthLb = new Label();
        monthLb.setText(MONTH_LB);

        Label hourLb = new Label();
        hourLb.setText(HOUR_LB);

        Label minuteLb = new Label();
        minuteLb.setText(MINUTE_LB);

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
        day.setPrefWidth(54.5);

        ChoiceBox<Integer> month = new ChoiceBox<>();
        month.setItems(months);
        month.setPrefWidth(54.5);

        ChoiceBox<Integer> hour = new ChoiceBox<>();
        hour.setItems(hours);
        hour.setPrefWidth(54.5);

        ChoiceBox<Integer> minute = new ChoiceBox<>();
        minute.setItems(minutes);
        minute.setPrefWidth(54.5);

        SingleSelectionModel<Integer> daySelModel = day.getSelectionModel();
        daySelModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> newValueD = newValue);

        SingleSelectionModel<Integer> monthSelModel = month.getSelectionModel();
        monthSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueM = newValue));

        SingleSelectionModel<Integer> hourSelModel = hour.getSelectionModel();
        hourSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueH = newValue));

        SingleSelectionModel<Integer> minuteSelModel = minute.getSelectionModel();
        minuteSelModel.selectedItemProperty().addListener(((observable, oldValue, newValue) -> newValueMi = newValue));

        Button btDone = new Button(BT_DONE);
        btDone.setPrefSize(W_BT_DEF+43, H_BT_DEF-8);
        btDone.setOnAction(event -> {
            if (newValueD != 0 && newValueM != 0 && newValueH != 0 && newValueMi != 0) {
                try {
                    createNotify();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();
            } else Toolkit.getDefaultToolkit().beep();
        });

        BorderPane dateLbPane = new BorderPane();
        dateLbPane.setLeft(dayLb);
        dateLbPane.setRight(monthLb);

        BorderPane timeLbPane = new BorderPane();
        timeLbPane.setLeft(hourLb);
        timeLbPane.setRight(minuteLb);

        BorderPane dateTimeLbPane = new BorderPane();
        dateTimeLbPane.setLeft(dateLbPane);
        dateTimeLbPane.setRight(timeLbPane);

        BorderPane lbsPane = new BorderPane();
        lbsPane.setCenter(dateTimeLbPane);

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

        root.setTop(lbsPane);
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
                Toolkit.getDefaultToolkit().beep();
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

            ActionListener exitListener = e -> {
                isExiting = true;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.exit(0);
            };
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