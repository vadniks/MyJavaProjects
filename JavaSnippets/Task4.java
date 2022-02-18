
import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Task4 {

    public static void main(String[] args) {
        final String
                rsl = "Result: ",
                btw = " X ",
                lts = "Last Scorer: ",
                sna = "N/b.b.A",
                wnr = "Winner: ",
                drw = "DRAW",
                acm = "AC Milan",
                rlm = "Real Madrid";

        var frm = new JFrame("Title");
        var pnl = new JPanel();
        var bxl = new BoxLayout(pnl, BoxLayout.PAGE_AXIS);

        var lb1 = new JLabel(rsl + 0 + btw + 0);
        pnl.add(lb1);

        var lb2 = new JLabel(lts + sna);
        pnl.add(lb2);

        var lb3 = new JLabel(wnr + drw);
        pnl.add(lb3);

        var cn1 = new AtomicInteger(0);
        var cn2 = new AtomicInteger(0);

        Function<Boolean, Void> hdl = (Boolean wh) -> {
            (wh ? cn1 : cn2).getAndIncrement();

            lb1.setText(rsl + cn1 + btw + cn2);
            lb2.setText(lts + (wh ? acm : rlm));

            lb3.setText(wnr + (cn1.intValue() > cn2.intValue() ? acm : rlm));

            return null;
        };

        var bt1 = new JButton(acm);
        bt1.addActionListener(actionEvent -> hdl.apply(true));
        pnl.add(bt1);

        var bt2 = new JButton(rlm);
        bt2.addActionListener(actionEvent -> hdl.apply(false));
        pnl.add(bt2);

        frm.add(pnl);
        frm.setSize(150, 160);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
