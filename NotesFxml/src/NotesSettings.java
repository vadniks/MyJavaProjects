import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
public class NotesSettings {
    static Stage primaryStage;

    static void createWindow() {
        primaryStage = new Stage();
        primaryStage.setTitle("Create note");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(NotesMain.primaryStage);
        primaryStage.setOnCloseRequest(event -> MainCtrl.updateList());
        initRootLayout();
    }

    private static void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotesCreateNote.class.getResource("NSettings.fxml"));
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