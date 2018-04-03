import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Vad Nik.
 * @version dated Mar 25, 2018.
 */
public class NotesMain extends Application {
    static Stage primaryStage;

    @Override
    public void start(Stage pStage) throws Exception {
        primaryStage = pStage;
        primaryStage.setTitle("Notes");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotesMain.class.getResource("NMain.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.show();

            if (new File("serv.txt").exists()) {
                if (Vars.vars.isPasswordEnable) {
                    SecurityProcessing.createWindow();
                    primaryStage.setOpacity(0);
                }
            }

//            FileReader reader = new FileReader("serv.txt");
//            String text = null;
//            int b;
//            while ((b = reader.read()) != -1)
//                text += (char) b;
//            System.out.println(text);

            Platform.setImplicitExit(false);
            NotifyProcessing.tray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotUsed
    private static void initThreads() {
        Thread th = Thread.currentThread();
        new Thread(() -> {
            try {
                FileReader readerTh = new FileReader("service.txt");
                while (th.isAlive()) {
                    String servTh = null;
                    int bTh;
                    while ((bTh = readerTh.read()) != -1)
                        servTh += (char) bTh;
                    if (servTh != null)
                        throw new NotesAppAlreadyRunningException("Notes app is running!");
                }
                readerTh.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();

        class CathTheNAARE implements Thread.UncaughtExceptionHandler {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof NotesAppAlreadyRunningException) {
                    showMessage();
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(new CathTheNAARE());
    }

    private static String checkAppRunning() throws IOException {
        File file = new File("service.txt");

        if (!file.exists())
            file.createNewFile();

        FileReader reader = new FileReader("service.txt");
        String serv = null;
        int b;
        while ((b = reader.read()) != -1)
            serv += (char) b;
        reader.close();
        return serv;
    }

    private static void showMessage() {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "Notes app is running! You can't run this app twice!", "Error",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private static void showMessage(String message) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private static void showMessage2(String message) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private static void setRunningFlag() {
        try(FileWriter writer = new FileWriter("service.txt")) {
            writer.write("1");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void handleExceptions() {
        class CathTheNAARE implements Thread.UncaughtExceptionHandler {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof NotesAppAlreadyRunningException) {
                    showMessage("An error has been occurred");
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(new CathTheNAARE());
    }

    static void exit() {
        try(FileWriter writer = new FileWriter("service.txt")) {
            writer.write("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //if (Vars.vars.isPasswordEnable)
            SecurityProcessing.serialize();
        try {
            SecurityProcessing.encryptDb();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("serv.txt");

        File file2 = new File("notes.db");
        if (!file.exists() && file2.exists()) {
            file2.delete();
            Vars.vars = new Vars();
            SecurityProcessing.serialize();
            showMessage2("Files are corrupted! Re-install application.");
            System.exit(1);
        }

        if (!file.exists() && !file2.exists()) {
            Vars.vars = new Vars();
            SecurityProcessing.serialize();
        }

        try {
            if (checkAppRunning() == null) {

                if (!System.getProperty("os.name").split(" ")[0].matches("^([W]|[w])[i][n][d][o][w][s]")) {
                    int res = JOptionPane.showOptionDialog(null,
                            "You'r OS isn't windows. It might cause problems. Would tou like to proceed?", "Warning",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                    if (!(res == 0))
                        System.exit(0);
                }

                handleExceptions();
                setRunningFlag();
                if (new File("serv.txt").exists())
                    Vars.vars = SecurityProcessing.deserialize();
                else {
                    Vars.vars = new Vars();
                    SecurityProcessing.serialize();
                }
                try {
                    launch(args);
                } catch (Exception ex) {
                    String message = "An error has been occurred!\n" + ex.getMessage();
                    FileWriter writer = new FileWriter("log.txt");
                    writer.write(message);
                    ex.printStackTrace();
                    showMessage(message);
                }
            }
            else
                throw new NotesAppAlreadyRunningException("Notes app is running!");
        } catch (NotesAppAlreadyRunningException ex) {
            showMessage();
        } catch (Exception ex) {
            showMessage("An error has been occurred!");
        }
    }
}