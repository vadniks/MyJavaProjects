import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Vad Nik.
 * @version dated Mar 27, 2018.
 */
@ToString
@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String text;

    Note() { }

    Note(int id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    Note(String name, String text) {
        this.name = name;
        this.text = text;
    }
}