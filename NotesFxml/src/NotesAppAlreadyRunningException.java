/**
 * @author Vad Nik.
 * @version dated Mar 31, 2018.
 */
public class NotesAppAlreadyRunningException extends RuntimeException {

    public NotesAppAlreadyRunningException() {
        super();
    }

    public NotesAppAlreadyRunningException(String message) {
        super(message);
    }

    public NotesAppAlreadyRunningException(Throwable cause) {
        super(cause);
    }

    public NotesAppAlreadyRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}