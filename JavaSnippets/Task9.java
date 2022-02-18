import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Task9 {

    public static void main(String[] args) {
        new LabClassUI();
//        new Client();
    }

    static class Bool {
        boolean a;
        public Bool(boolean a) { this.a = a; }
    }

    static class Client {

        static class BadITNException extends IllegalArgumentException {}

        Client() {
            var a = new Scanner(System.in);
            var b = a.nextInt();
            if (b <= 0) throw new BadITNException();
        }
    }

    static class LabClassDriver {
        private final LabClass a;
        private final Bool b = new Bool(false);

        public LabClassDriver(LabClass a)
        { this.a = a; }

        void crt() {
            a.arr = new LabClass.Arr();
            a.arr.add(new Student(0, "c", b));
            a.arr.add(new Student(4, "d", b));
            a.arr.add(new Student(5, "a", b));
            a.arr.add(new Student(9, "b", b));
        }

        void srt(boolean c) {
            b.a = c;
            System.out.println(b);
            Collections.sort(a.arr);
        }

        <T extends Comparable<?>> Student src(T c) {
            for (var d : a.arr)
                if ((c instanceof Integer ? (Integer) d.a : d.b).equals(c))
                    return d;
            return null;
        }
    }

    static class LabClassUI implements ActionListener {
        private final LabClass lc = new LabClass();
        private final LabClassDriver lcd = new LabClassDriver(lc);
        private boolean b;
        private final JTextArea txt = new JTextArea();

        LabClassUI() {
            var pnl = new JPanel();
            pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

            txt.setFont(txt.getFont().deriveFont(15f));
            txt.setLineWrap(true);
            pnl.add(txt);

            var src = new JButton("Search");
            src.setName(String.valueOf(0));
            src.addActionListener(this);
            pnl.add(src);

            var chk = new JCheckBox("By name");
            chk.setSelected(false);
            chk.addActionListener(this);
            pnl.add(chk);

            var srt = new JButton("Sort");
            srt.setName(String.valueOf(1));
            srt.addActionListener(this);
            pnl.add(srt);

            var rst = new JButton("Reset");
            rst.setName(String.valueOf(2));
            rst.addActionListener(this);
            pnl.add(rst);

            var frm = new JFrame("Title");
            frm.setSize(new Dimension(400, 300));
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frm.setLocationRelativeTo(null);
            frm.add(pnl);
            frm.setVisible(true);

            lcd.crt();
            txt.setText(lc.arr.toString());
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() instanceof JCheckBox) {
                ((JCheckBox) actionEvent.getSource()).setSelected((b = !b));
                return;
            }

            var a = (JButton) actionEvent.getSource();
            if (a.getName().equals(String.valueOf(0))) {
                if (txt.getText().equals(""))
                    throw new EmptyStringException();

                var c = lcd.src(b ? txt.getText() : Integer.parseInt(txt.getText()));
                if (c == null) throw new StudentNotFoundException();

                txt.setText(!b ? c.b : String.valueOf(c.a));
            } else if (a.getName().equals(String.valueOf(1))) {
                lcd.srt(b);
                txt.setText(lc.arr.toString());
            } else if (a.getName().equals(String.valueOf(2))) {
                lc.arr = null;
                lcd.crt();
                txt.setText(lc.arr.toString());
            }
        }
    }

    static class LabClass {
        Arr arr;
        static class Arr extends ArrayList<Student> {}
    }

    static class Student implements Comparable<Student> {
        int a; String b; Bool c;

        Student(int a, String nm, Bool c) {
            if (nm.equals(""))
                throw new EmptyStringException();
            this.a = a; this.b = nm; this.c = c;
        }

        @Override public int compareTo(Student b)
        { return c.a ?
                String.CASE_INSENSITIVE_ORDER.compare(b.b, this.b) :
                Integer.compare(b.a, a); }

        @Override public String toString()
        { return String.format("(%d, '%s')", a, b); }
    }

    static class StudentNotFoundException extends RuntimeException {}

    static class EmptyStringException extends IllegalStateException {}
}
