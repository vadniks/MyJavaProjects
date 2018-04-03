import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Vad Nik.
 * @version dated Mar 25, 2018.
 */
public class NotifyProcessing {
    static int newValueD;
    static int newValueM;
    static Stage primaryStage;
    private String nameT;
    private String textT;
    private static TrayIcon trayIcon;
    static int newValueH;
    static int newValueMi;
    static boolean day = false;
    static boolean month = false;
    static boolean hour = false;
    static boolean minute = false;

    NotifyProcessing(String name, String text) {
        nameT = name;
        textT = text;
        primaryStage = new Stage();
        primaryStage.setTitle("Set notification");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesCreateNote.primaryStage);
        initRootLayout();
    }

    private static void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotesCreateNote.class.getResource("NNotifyProcessing.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createNotify() throws ParseException {
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
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Notification. Reminder for note:");
                    alert.setContentText(nameT + "\n" + textT);
                    alert.setTitle("Notification");
                    alert.initOwner(NotesMain.primaryStage);
                    alert.setResizable(false);
                    alert.setOnShowing(event1 -> {
                        Toolkit.getDefaultToolkit().beep();
                        Vars.isAlertNPShowing = true;
                    });
                    alert.setOnCloseRequest(event -> Vars.isAlertNPShowing = false);
                    alert.show();
                });
                //trayIcon.displayMessage("Notify: " + nameT, textT, TrayIcon.MessageType.INFO);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, calendar.getTime());
    }

    static void tray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(NotesMain.class.getResource("notes.png"));

            ActionListener exitListener = e -> NotesMain.exit();
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
                        if (!Vars.isAlertNPShowing)
                            Platform.runLater(() -> NotesMain.primaryStage.show());
                        else
                            Toolkit.getDefaultToolkit().beep();
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