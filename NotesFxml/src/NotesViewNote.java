import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Vad Nik.
 * @version dated Mar 27, 2018
 */
public class NotesViewNote {
    static Stage primaryStage;
    static String tempName;
    static String tempText;
    static int item;

    static void createWindow(String name, String text) {
        tempName = name;
        tempText = text;
        primaryStage = new Stage();
        primaryStage.setTitle("View note");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesMain.primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            MainCtrl.updateList();
            try {
                MainCtrl.selModel.clearSelection();
            } catch (Exception ignored) { }
        });
        initRootLayout();
    }

    private static void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotesCreateNote.class.getResource("NViewNote.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void close() {
        primaryStage.close();
    }
}