import javafx.scene.layout.BorderPane;

/**
 * Interface describing a base method {@code window(Border pane)} and
 * the final constants describing the base params like window width.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 14, 2018
 */
public interface IConstants {

    /**
     *Describes the arrangement of the window.
     *
     * @param root is a root pane.
     */
    void window(BorderPane root);

    /**
     * Final constants.
     */
    String TITLE_MAIN = "Notes";
    int WIDTH = 350;
    int HEIGHT = 450;
    String BT_CREATE = "Create note";
    String BT_CLEAR_LIST = "Clear";
    String LABEL = "Your notes:";
    int W_BT_DEF = 175;
    int H_BT_DEF = 35;
    int W_BT_3 = 116;
    String TITLE_CREATE = "Create note";
    String BT_DONE = "Done";
    String BT_CLEAR = "Clear text";
    String NOT_ANY_LB = "You haven't any notes yet!";
    String TITLE_VIEW = "View: ";
    String BT_DELETE = "Delete note";
    String BT_EDIT = "Edit note";
    String BT_DATE = "Set notify";
    String TITLE_NOTIFY = "Set notify";
    String TF_TM_FORMAT = "12-00";
    String TF_TOOLTIP = "Set time";
    String TOOLTIP_LB = "           Set datetime:";
}