import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
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
    private boolean day = false;
    private boolean month = false;
    private boolean hour = false;
    private boolean minute = false;

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
        Label tlLb = new Label();
        tlLb.setFont(NotesMain.font);
        tlLb.setText(TOOLTIP_LB);

        DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(event -> {
            LocalDate lcDate = datePicker.getValue();
            if (lcDate.toString().matches("([2][0]\\d\\d[-][0-1]\\d[-][0-3]\\d)")) {
                newValueD = lcDate.getDayOfMonth();
                day = true;
                newValueM = lcDate.getMonth().getValue();
                month = true;
            }
        });

        TextField timeFl = new TextField();
        timeFl.setPromptText(TF_TM_FORMAT);
        timeFl.setTooltip(new Tooltip(TF_TOOLTIP));
        timeFl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("([0-23]\\d[-][0-59]\\d)")) {
                newValueH = Integer.parseInt(newValue.split("-")[0]);
                hour = true;
                newValueMi = Integer.parseInt(newValue.split("-")[1]);
                minute = true;
            }
        });

        Button btDone = new Button(BT_DONE);
        btDone.setPrefSize(W_BT_DEF+135, H_BT_DEF-8);
        btDone.setOnAction(event -> {
            if (day && month && hour && minute) {
                try {
                    createNotify();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();
            } else Toolkit.getDefaultToolkit().beep();
        });

        BorderPane dateTimePane = new BorderPane();
        dateTimePane.setLeft(datePicker);
        dateTimePane.setRight(timeFl);

        root.setTop(tlLb);
        root.setCenter(dateTimePane);
        root.setBottom(btDone);
    }


    /**
     * Creates a reminder by means of notification.
     *
     * @throws ParseException just not to handle this exception.
     */
    private void createNotify() throws ParseException {
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