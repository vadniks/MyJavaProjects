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
    String ENTER_NAME_LB = "Enter name:";
    String BT_DONE = "Done";
    String ENTER_TEXT_LB = "Enter text of note below:";
    String BT_CLEAR = "Clear text";
    String NOT_ANY_LB = "You haven't any notes yet!";
    String TITLE_VIEW = "View: ";
    String BT_DELETE = "Delete note";
    String BT_EDIT = "Edit note";
    String BT_DATE = "Set notify";
    String TITLE_NOTIFY = "Set notify";
    String SET_DATE_LB = "Set date for notification:";
    String DAY_LB = "Set day";
    String MONTH_LB = "Set month";
    String HOUR_LB = "Set hour";
    String MINUTE_LB = "Set minute";
}