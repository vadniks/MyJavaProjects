import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Lab5 {

    public static void main(String[] args) {
        var frm = new JFrame("Title");
        frm.setSize(new Dimension(1000, 600));

        var pnl = new JPanel();
        pnl.setLayout(new OverlayLayout(pnl));

//        t1(pnl);
//        try {
//            t2(pnl, new String[]{ "/home/admin/IdeaProjects/JavaTasks/res/manjaro-gnome.jpg" });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        t3(pnl);

        frm.add(pnl);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        frm.setVisible(true);
    }

    static void t1(JPanel pnl) {
        var r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            Color c = switch (r.nextInt(5)) {
                case 0 -> Color.BLACK;
                case 1 -> Color.GREEN;
                case 2 -> Color.CYAN;
                case 3 -> Color.BLUE;
                case 4 -> Color.DARK_GRAY;
                case 5 -> Color.MAGENTA;
                default -> Color.ORANGE;
            };
            Shape a = r.nextBoolean() ? new Rectangle(
                    c,
                    r.nextInt(900),
                    r.nextInt(500),
                    r.nextInt(100),
                    r.nextInt(100)
            ) : new Circle(
                    c,
                    r.nextInt(900),
                    r.nextInt(500),
                    r.nextInt(100),
                    r.nextInt(100)
            );
            pnl.add(a);
        }
    }

    static void t2(JPanel pnl, String[] args) throws IOException {
        var img = ImageIO.read(new File(args[0]));
        var lb = new JLabel(new ImageIcon(img));
        lb.setMaximumSize(new Dimension(256, 128));
//        lb.setLocation(new Point());
        pnl.add(lb);
    }

    static void t3(JPanel pnl) {
        var lb = new JLabel(new ImageIcon("/home/admin/IdeaProjects/JavaTasks/res/rickroll-dance.gif"));
        lb.setMaximumSize(new Dimension(256, 128));
        pnl.add(lb);
    }

    static class Shape extends Component {
        Color color;
        int x;
        int y;
        int w;
        int h;

        public Shape(Color color, int x, int y, int w, int h) {
            super();
            this.color = color;
            this.x = x;
            this.y = y;
            setLocation(x, y);
            setBounds(x, y, w, h);
            this.w = w;
            this.h = h;
        }
    }

    static class Circle extends Shape {

        public Circle(Color color, int x, int y, int w, int h) {
            super(color, x, y, w, h);
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, w, h);
        }
    }

    static class Rectangle extends Shape {

        public Rectangle(Color color, int x, int y, int w, int h) {
            super(color, x, y, h, w);
        }

        @Override
        public void paint(Graphics g) {
            g.fillRect(x, y, w, h);
            g.dispose();
        }
    }
}
