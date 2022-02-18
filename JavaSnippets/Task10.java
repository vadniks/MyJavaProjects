import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task10 {

    @SuppressWarnings("unused")
    abstract static class  _1 {

        static class Cmplx {
            int r, i;
            public Cmplx(int _r, int _i) { r = _r; i = _i; }
        }

        interface CmplxAF {
            Cmplx createCmplx();
            Cmplx createCmplx(int r, int i);
        }

        @SuppressWarnings("unused")
        static class CncrtF implements CmplxAF {
            @Override public Cmplx createCmplx() { return new Cmplx(0, 0); }
            @Override public Cmplx createCmplx(int r, int i) { return new Cmplx(r, i); }
        }
    }

    @SuppressWarnings("unused")
    static abstract class _2 {

        interface Chair {}

        static class AChair implements Chair {
            private int age;
            AChair(int _age) { age = _age; }
            int getAge() { return age; }
        }

        static class BChair implements Chair { void doMagic() {} }
        static class CChair implements Chair { int sum(int a, int b) { return 0; } }

        interface AChairF {
            AChair createAChair();
            BChair createBChair();
            CChair createCChair();
        }

        @SuppressWarnings("unused")
        static class ChairF implements AChairF {
            @Override public AChair createAChair() { return new AChair(0); }
            @Override public BChair createBChair() { return new BChair(); }
            @Override public CChair createCChair() { return new CChair(); }
        }

        @SuppressWarnings("unused")
        static class Client {
            private Chair a;
            void sit(Chair b) {}
            void setA(Chair b) { a = b; }
        }
    }

    static abstract class _3 {

        interface IDoc { Object inf(); }
        class ImgDoc implements IDoc { @Override public Object inf() { return null; }; }
        class MusDoc implements IDoc { @Override public Object inf() { return null; }; }

        interface ICrtDoc {
            IDoc crtNew();
            IDoc crtOpn();
            void sv(Object a);
        }

        static class TxtDoc implements IDoc {
            String txt;
            TxtDoc(String a) { txt = a; }
            @Override public Object inf() { return txt; };
        }

        static class CrtTxtDoc implements ICrtDoc {
            public String lst;
            @Override public IDoc crtNew() { return new TxtDoc(null); }
            @Override public IDoc crtOpn() { return new TxtDoc(lst); }
            @Override public void sv(Object a) { lst = (String) a; }
        }

        static class Frm extends JFrame implements ActionListener {
            private final String[] a = { "File", "New", "Open", "Save", "Exit" };
            private final ICrtDoc crdc;
            private IDoc dc;

            Frm(ICrtDoc _a) {
                crdc = _a;

                var c = new JMenu(a[0]);

                var b = new JMenuItem[a.length];
                for (int i = 1; i < a.length; i++) {
                    b[i] = new JMenuItem(a[i]);
                    b[i].addActionListener(this);
                    c.add(b[i]);
                }

                var jmb = new JMenuBar();
                jmb.add(c);
                setJMenuBar(jmb);

                setSize(400, 200);
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                var b = ((JMenuItem) actionEvent.getSource()).getText();

                if (b.equals(a[1])) {
                    dc = crdc.crtNew();
                    System.out.println("New: " + dc.inf());
                } else if (b.equals(a[2])) {
                    dc = crdc.crtOpn();
                    System.out.println("Open: " + dc.inf());
                } else if (b.equals(a[3])) {
                    crdc.sv("smth");
                    System.out.println("Save");
                } else
                    System.exit(0);
            }

            public static void main(String[] args) { new Frm(new CrtTxtDoc()); }
        }
    }
}
