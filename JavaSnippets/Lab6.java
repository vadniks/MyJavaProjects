import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab6 {

    public static void main(String[] args) throws Exception {
//        t1();
//        t2();
        t3();
    }

    static void t1() {
        JFrame frm = new JFrame("Title");

        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

        JTextField fld = new JTextField();
        pnl.add(fld);

        AtomicInteger a = new AtomicInteger();
        Random r = new Random(System.currentTimeMillis());
        AtomicInteger b = new AtomicInteger(r.nextInt(20));

        JTextPane tpn = new JTextPane();
        tpn.setEditable(false);
        tpn.setSize(100, 50);
        pnl.add(tpn);

        JButton btn = new JButton("Guess");
        btn.addActionListener(actionEvent -> {
            if (a.get() < 3) {
                int c;
                try {
                    c = Integer.parseInt(fld.getText());
                } catch (Exception e) {
                    tpn.setText("Wrong");
                    a.getAndIncrement();
                    fld.setText("");
                    return;
                }
                if (c == b.get()) {
                    tpn.setText("Right");
                    a.set(0);
                    b.set(r.nextInt(20));
                    fld.setText("");

                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }).start();
                } else {
                    System.out.println(b);
                    tpn.setText("Wrong: " + (b.get() > c ? "number is greater" : "number is less"));
                    a.getAndIncrement();
                    fld.setText("");
                }
            } else {
                tpn.setText("Game over");
                a.set(0);
                b.set(r.nextInt(20));
                fld.setText("");

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }).start();
            }
        });
        pnl.add(btn);

        frm.add(pnl);
        frm.setSize(400, 250);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

    static void t2() {
        abstract class A implements MouseListener {
            public void mouseClicked(MouseEvent mouseEvent) {}
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public abstract void mouseEntered(MouseEvent mouseEvent);
            public void mouseExited(MouseEvent mouseEvent) {}
        }

        JFrame frm = new JFrame("Title");
        frm.setSize(800, 600);

        var pnl = new JPanel(new GridLayout(3, 3));

        var pn1 = new JPanel();
        pn1.setBackground(Color.GRAY);
        pnl.add(pn1);

        var pn2 = new JPanel();
        pn2.setBackground(Color.DARK_GRAY);
        pn2.add(new JLabel("North"));
        pn2.addMouseListener(new A() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!frm.hasFocus())
                    return;
                JOptionPane.showMessageDialog(frm, "Welcome to");
            }
        });
        pnl.add(pn2);

        var pn3 = new JPanel();
        pn3.setBackground(Color.GRAY);
        pnl.add(pn3);

        var pn4 = new JPanel();
        pn4.setBackground(Color.DARK_GRAY);
        pn4.add(new JLabel("West"));
        pn4.addMouseListener(new A() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!frm.hasFocus())
                    return;
                JOptionPane.showMessageDialog(frm, "Welcome to Dzhidda");
            }
        });
        pnl.add(pn4);

        var pn5 = new JPanel();
        pn5.setBackground(Color.GRAY);
        pn5.add(new JLabel("Center"));
        pn5.addMouseListener(new A() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!frm.hasFocus())
                    return;
                JOptionPane.showMessageDialog(frm, "Welcome to");
            }
        });
        pnl.add(pn5);

        var pn6 = new JPanel();
        pn6.setBackground(Color.DARK_GRAY);
        pn6.add(new JLabel("East"));
        pn6.addMouseListener(new A() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!frm.hasFocus())
                    return;
                JOptionPane.showMessageDialog(frm, "Welcome to Dahrane");
            }
        });
        pnl.add(pn6);

        var pn7 = new JPanel();
        pn7.setBackground(Color.GRAY);
        pnl.add(pn7);

        var pn8 = new JPanel();
        pn8.setBackground(Color.DARK_GRAY);
        pn8.add(new JLabel("South"));
        pn8.addMouseListener(new A() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!frm.hasFocus())
                    return;
                JOptionPane.showMessageDialog(frm, "Welcome to Abha");
            }
        });
        pnl.add(pn8);

        var pn9 = new JPanel();
        pn9.setBackground(Color.GRAY);
        pnl.add(pn9);

        frm.add(pnl);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

    static void t3() throws IOException, FontFormatException {
        String[] nms = {
                "Blue",
                "Red",
                "Black",
                "Times New Roman",
                "MS Sans Serif",
                "Courier New"
        };

        var txt = new JTextPane();

        var f1 = Font.createFont(
                Font.PLAIN,
                new File("/home/admin/IdeaProjects/JavaTasks/res/times new roman.ttf"));
        var f2 = Font.createFont(
                Font.PLAIN,
                new File("/home/admin/IdeaProjects/JavaTasks/res/cour.ttf"));

        var g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        g.registerFont(f1);
        g.registerFont(f2);

        ActionListener act = (ActionEvent a) -> {
            var b = ((JMenuItem) a.getSource()).getText();

            var stc = StyleContext.getDefaultStyleContext();
            AttributeSet ol = txt.getCharacterAttributes();

            if (b.equals(nms[0]))
                ol = stc.addAttribute(ol, StyleConstants.Foreground, Color.BLUE);
            else if (b.equals(nms[1]))
                ol = stc.addAttribute(ol, StyleConstants.Foreground, Color.RED);
            else if (b.equals(nms[2]))
                ol = stc.addAttribute(ol, StyleConstants.Foreground, Color.BLACK);
            else if (b.equals(nms[3]))
                ol = stc.addAttribute(ol, StyleConstants.FontFamily, f1.getName());
            else if (b.equals(nms[4]))
                ol = stc.addAttribute(ol, StyleConstants.FontFamily, Font.SANS_SERIF);
            else if (b.equals(nms[5]))
                ol = stc.addAttribute(ol, StyleConstants.FontFamily, f2.getName());

            txt.setCaretPosition(0);
            txt.setCharacterAttributes(ol, false);

            var t = txt.getText();
            txt.setText("");
            txt.replaceSelection(t);
        };

        var clr = new JMenu("Color");
        JMenuItem[] cls = {
                new JMenuItem(nms[0]),
                new JMenuItem(nms[1]),
                new JMenuItem(nms[2])
        };
        for (var i : cls) {
            i.addActionListener(act);
            clr.add(i);
        }

        var fnt = new JMenu("Font");
        JMenuItem[] fns = {
                new JMenuItem(nms[3]),
                new JMenuItem(nms[4]),
                new JMenuItem(nms[5])
        };
        for (var i : fns) {
            i.addActionListener(act);
            fnt.add(i);
        }

        JFrame frm = new JFrame("Title");
        frm.setSize(400, 200);

        var mnb = new JMenuBar();
        mnb.add(clr);
        mnb.add(fnt);
        frm.setJMenuBar(mnb);

        var pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));
        pnl.add(txt);

        frm.add(pnl);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
