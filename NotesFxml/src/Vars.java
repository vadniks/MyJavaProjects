import java.io.*;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
public class Vars implements Serializable {
    private static final long serialVersionUID = 8025806313174535576L;

    static transient NotifyProcessing np;
    static transient boolean isAlertNPShowing;
    String password;
    boolean isPasswordEnable;

    static Vars vars;

    @NotUsed
    String info() {
        return password + "~~" + isPasswordEnable;
    }

    @NotUsed
    void setInfo(String info) {
        password = info.split("~~")[0];
        isPasswordEnable = Boolean.parseBoolean(info.split("~~")[1]);
    }
}