import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static java.lang.System.arraycopy;

public class Task31_32 {
    final static String[] drnks = { "beer", "wine", "vodka", "brandy", "champagne", "whisky", "tequila",
            "rum", "vermuth", "liquior", "jagermister", "juice", "coffee", "green tea", "black tea",
            "milk", "water", "soda" };
    static Cstm cstmr = null;

    public static void main(String[] args) {
        var nm = new JTextField();

        var vw = new JTextArea();
        vw.setLineWrap(true);
        vw.setEditable(false);

        var tbor = new TblOrdMng();
        var inor = new IntOrdMng();;
        var tbin = new AtomicBoolean(false);

        var lstmdl = new DefaultListModel<String>();
        for (var _i : drnks)
            lstmdl.addElement(_i);

        var drks = new JList<>(lstmdl);
        drks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        drks.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        var scrl = new JScrollPane(drks);

        var tmsmdl = new DefaultListModel<String>();

        var tms = new JList<>(tmsmdl);
        tms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tms.setEnabled(false);

        var scrl2 = new JScrollPane(tms);

        var fr = new JFrame();

        Function<Boolean, Void> bta = a -> {
            if (cstmr == null) {
                vw.setText("set customer");
                return null;
            }

            if (a && drks.isSelectionEmpty()) {
                vw.setText("select drink");
                return null;
            }

            if (a && cstmr != null && cstmr.ag < 18) {
                vw.setText("age is lower then 18");
                return null;
            }

            if (!a && nm.getText().equals("")) {
                vw.setText("enter dish name");
                return null;
            }

            int d = Math.abs(new Random().nextInt() / 1000000 + 1);
            var e = nm.getText();

            var c = a ? new Drk(d,
                    drks.getSelectedValue()/* +
                            '_' +
                            e*/, "",
                    Dte.prs(drks.getSelectedValue())) : new Dsh(d, e, "");

            drks.clearSelection();

            if (!tbin.get()) {
                var b = new TblOrd();
                b.st(cstmr);
                b.dd(c);
                tbor.dd(b, tbor.ors.length);
            } else {
                var b = new IntOrd();
                b.st(cstmr);
                b.dd(c);
                inor.dd(b);
            }
            return null;
        };

        var dr = new JButton("Order a drink");
        dr.addActionListener(a -> bta.apply(true));

        var ds = new JButton("Order a dish");
        ds.addActionListener(a -> bta.apply(false));

        var cb = new JCheckBox("Is internet order?");
        cb.addActionListener(a -> {
            tbin.set(!tbin.get());
            cb.setSelected(tbin.get());
        });

        var ls = new JButton("View ordered items");
        ls.addActionListener(a -> {
            tmsmdl.clear();

            var b = 0;
            var e = 0;
            if (!tbin.get()) {
                for (var c : tbor.ors()) {
                    for (var d : c.aro()) {
                        tmsmdl.addElement(d.toString());
                        b++;
                    }
                    e += c.smc();
                }
            } else {
                for (var c : inor.ors()) {
                    for (var d : c.aro()) {
                        tmsmdl.addElement(d.toString());
                        b++;
                    }
                    e += c.smc();
                }
            }
            tms.setModel(tmsmdl);
            vw.setText("cost sum=" + e + ", quantity=" + b);
        });

        var rm = new JButton("Delete ordered item");
        rm.addActionListener(a -> {
            if (!tbin.get()) {
                for (var b : tbor.ors())
                    b.rm(nm.getText());
            } else
                for (var b : inor.ors())
                    b.rm(nm.getText());
        });

        var cstnm = new JTextField();
        cstnm.setToolTipText("name");
        var cstlnm = new JTextField();
        cstlnm.setToolTipText("last name");
        var cstag = new JTextField();
        cstag.setToolTipText("age");

        var cstm = new JButton("Set customer");
        cstm.addActionListener(_a -> cstmr =
                new Cstm(cstlnm.getText(), cstlnm.getText(), Integer.parseInt(cstag.getText()), Adr.emp));
        var cstm2 = new JButton("Get customer");
        cstm2.addActionListener(_a -> {
            if (cstmr == null)
                return;

            cstnm.setText(cstmr.fn);
            cstlnm.setText(cstmr.sn);
            cstag.setText(String.valueOf(cstmr.ag));
        });

        var pn = new JPanel();
        pn.setLayout(new BoxLayout(pn, BoxLayout.PAGE_AXIS));
        pn.add(nm);
        pn.add(scrl2);
        pn.add(vw);

        pn.add(dr);
        pn.add(ds);
        pn.add(cb);
        pn.add(ls);
        pn.add(rm);

        pn.add(scrl);

        pn.add(cstm);
        pn.add(cstnm);
        pn.add(cstlnm);
        pn.add(cstag);
        pn.add(cstm2);

        fr.add(pn);
        fr.setSize(500, 700);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }

    static <T> void p(T a) { System.out.println(a); }

    static abstract class MnuItm {
        final int cst;
        final String nm, dsc;

        MnuItm(int cst, String nm, String dsc)
        { this.cst = cst; this.nm = nm; this.dsc = dsc; }

        int gCst() { return cst; }
        String gNm() { return nm; }
        String gDsc() { return dsc; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MnuItm mnuItm = (MnuItm) o;
            return cst == mnuItm.cst && Objects.equals(nm, mnuItm.nm) && Objects.equals(dsc, mnuItm.dsc);
        }

        @Override public String toString()
        { return '(' + getClass().getSimpleName() + ' ' + cst + ' ' + nm + /*' ' + dsc +*/ ')'; }
    }

    enum Dte {
        br(true), wn(true), vd(true), bn(true), ch(true), wh(true),
        tq(true), rm(true), vr(true), lq(true), jg(true), jc(false), cf(false),
        gt(false), bt(false), ml(false), wt(false), sd(false);
        boolean a;
        Dte(boolean b) { a = b; }

        static Dte prs(String a) { return switch (a) {
            case "beer" -> Dte.br;
            case "wine" -> Dte.wn;
            case "vodka" -> Dte.vd;
            case "brandy" -> Dte.br;
            case "champagne" -> Dte.ch;
            case "whisky" -> Dte.wh;
            case "tequila" ->Dte.tq;
            case "rum" -> Dte.rm;
            case "vermuth" -> Dte.vr;
            case "liquior" -> Dte.lq;
            case "jagermister" -> Dte.jg;
            case "juice" -> Dte.jc;
            case "coffee" -> Dte.cf;
            case "green tea" -> Dte.gt;
            case "black tea" -> Dte.bt;
            case "milk" -> Dte.ml;
            case "water" -> Dte.wt;
            case "soda" -> Dte.sd;
            default -> throw new IllegalArgumentException();
        }; }
    }

    interface Alc {
        boolean iad();
        double gav();
    }

    static class Drk extends MnuItm implements Alc {
        final double alvl;
        final Dte tp;

        Drk(int cst, String nm, String dsc, Dte tpp) {
            super(cst, nm, dsc);
            if (cst < 0 || nm == null || dsc == null)
                throw new IllegalArgumentException();
            tp = tpp;
            alvl = tp.a ? new Random().nextDouble() : 0.0d;
        }

        Dte gt() { return tp; }
        @Override public boolean iad() { return tp.a; }
        @Override public double gav() { return alvl; }

        @Override public String toString()
        { return '(' + getClass().getSimpleName() + ' ' + cst + ' ' + nm + ' ' + dsc +
                ' ' + iad() + (iad() ? " " + alvl + " " : "") + ')'; }
    }

    static class Dsh extends MnuItm {
        Dsh(int cst, String nm, String dsc) { super(cst, nm, dsc); }
    }

    static class Cstm {
        final String fn, sn;
        final int ag;
        final Adr adr;
        static final Cstm muc = new Cstm(null, null, 18, null);
        static final Cstm nuc = new Cstm(null, null, 17, null);

        public Cstm(String fn, String sn, int ag, Adr adr)
        { this.fn = fn; this.sn = sn; this.ag = ag; this.adr = adr; }

        String gfn() { return fn; }
        String gsn() { return sn; }
        int gag() { return ag; }
        Adr gad() { return adr; }
    }

    static class Adr {
        final String cn, sn;
        final int zc, bn, an;
        final char bl;
        static final Adr emp = new Adr(null, null, 0, 0, 0, (char) 0);

        public Adr(String cn, String sn, int zc, int bn, int an, char bl)
        { this.cn = cn; this.sn = sn; this.zc = zc; this.bn = bn; this.an = an; this.bl = bl; }

        String gcn() { return cn; }
        String gsn() { return sn; }
        int gzc() { return zc; }
        int gbn() { return bn; }
        int gan() { return an; }
        char gbl() { return bl; }
    }

    interface Ord {
        void pr();
        boolean dd(MnuItm a);
        String[] nms();
        int sz();
        int cnt(String a);
        int cnt(MnuItm a);
        MnuItm[] ar();
        boolean rm(String a);
        boolean rm(MnuItm a);
        int rma(String a);
        int rma(MnuItm a);
        MnuItm[] aro();
        int smc();
        Cstm gt();
        void st(Cstm a);
    }

    static class Nd {
        final MnuItm vl; Nd nx = null, pr = null;
        Nd(MnuItm vl) { this.vl = vl; }
    }

    static class IntOrd implements Ord {
        Nd rt = null, ls = null;
        int sz = 0;
        Cstm cst;

        @Override public Cstm gt() { return cst; }
        @Override public void st(Cstm a) { cst = a; }

        @Override
        public void pr() {
            var a = rt;
            while (a != null) {
                p(a.vl);
                a = a.nx;
            }
        }

        @Override
        public boolean dd(MnuItm a) {
            sz++;

            if (rt == null) {
                rt = new Nd(a);
                ls = rt;
                return true;
            }

            var b = rt;
            while (b.nx != null)
                b = b.nx;

            b.nx = new Nd(a);
            b.nx.pr = b;

            ls = b.nx;
            return true;
        }

        @Override
        public String[] nms() {
            var a = rt;
            var b = new ArrayList<String>();
            while (a != null) {
                b.add(a.vl.nm);
                a = a.nx;
            }
            return b.toArray(new String[0]);
        }

        @Override
        public boolean rm(String a) {
            var b = rt;
            Nd c = null;
            while (b != null) {
                if (b.vl.gNm().equals(a))
                    c = b;
                b = b.nx;
            }

            if (c != null) {
                var d = c.nx;
                var e = c.pr;

                if (d != null) d.pr = e;

                if (e != null) e.nx = d;
                else rt = d;

                sz--;
                return true;
            }
            return false;
        }

        @Override
        public boolean rm(MnuItm a) { return rm(a.nm); }

        @Override
        public int rma(String a) {
            var b = rt;
            var c = 0;

            while (b != null) {
                if (b.vl.gNm().equals(a)) {
                    var d = b.nx;
                    var e = b.pr;

                    if (d != null) d.pr = e;

                    if (e != null) e.nx = d;
                    else rt = d;

                    c++;
                }
                b = b.nx;
            }
            sz -= c;
            return c;
        }

        @Override
        public int rma(MnuItm a) { return rma(a.nm); }

        @Override public int sz() { return sz; }

        @Override
        public MnuItm[] ar() {
            var a = rt;
            var b = new MnuItm[0];

            while (a != null) {
                var c = new MnuItm[b.length + 1];
                arraycopy(b, 0, c, 0, b.length);
                b = c;

                b[b.length - 1] = a.vl;
                a = a.nx;
            }
            return b;
        }

        @Override
        public int smc() {
            var a = rt;
            var b = 0;
            while (a != null) {
                b += a.vl.gCst();
                a = a.nx;
            }
            return b;
        }

        @Override
        public int cnt(String a) {
            var b = rt;
            var c = 0;
            while (b != null) {
                if (b.vl.gNm().equals(a))
                    c++;
                b = b.nx;
            }
            return c;
        }

        @Override
        public int cnt(MnuItm a) { return cnt(a.nm); }

        @Override
        public MnuItm[] aro() {
            var a = ar();

            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    var b = a[i];
                    var c = a[j];

                    if (b.gNm().compareToIgnoreCase(c.gDsc()) > 0) {
                        a[i] = c;
                        a[j] = b;
                    }
                }
            }
            return a;
        }
    }

    static class TblOrd implements Ord {
        MnuItm[] tms = new MnuItm[0];
        Cstm cst;

        @Override public boolean dd(MnuItm a) {
            var c = new MnuItm[tms.length + 1];
            arraycopy(tms, 0, c, 0, tms.length);
            tms = c;
            tms[tms.length - 1] = a;
            return true;
        }

        @Override
        public String[] nms()
        { return Arrays.stream(tms).map(a -> a.nm).toList().toArray(new String[0]); }

        @Override public void pr() { p(Arrays.toString(tms)); }

        @Override
        public boolean rm(String a) {
            for (int i = 0; i < tms.length; i++)
                if (tms[i].nm.equals(a)) {
                    var b = new MnuItm[tms.length - 1];
                    arraycopy(tms, 0, b, 0, i);
                    arraycopy(tms, i + 1, b, i, tms.length - 1 - i);
                    tms = b;
                    return true;
                }
            return false;
        }

        @Override
        public boolean rm(MnuItm a) { return rm(a.nm); }

        @Override
        public int rma(String a) {
            var b = 0;
            for (MnuItm tm : tms)
                if (tm.nm.equals(a)) {
                    b++;
                    rm(tm.nm);
                }
            return b;
        }

        @Override public int rma(MnuItm a) { return rma(a.nm); }
        @Override public int sz() { return tms.length; }
        @Override public MnuItm[] ar() { return tms; }

        @Override
        public int smc() {
            var b = 0;
            for (var c : tms)
                b += c.cst;
            return b;
        }

        @Override public Cstm gt() { return cst; }
        @Override public void st(Cstm a) { cst = a; }

        @Override
        public int cnt(String a) {
            var b = 0;
            for (var c : tms)
                if (c.nm.equals(a))
                    b++;
            return b;
        }

        @Override
        public int cnt(MnuItm a) { return cnt(a.nm); }

        @Override public MnuItm[] aro()
        { return Arrays.stream(tms).sorted((a, b) -> a.dsc.compareToIgnoreCase(b.gDsc())).toList().toArray(new MnuItm[0]); }
    }

    interface OrdMng {
        int ict(String a);
        int ict(MnuItm a);
        Ord[] ors();
        int smc();
        int oct();
    }

    static class Qn {
        Qn nx, pr;
        Ord vl;
        public Qn(Ord vl) { this.vl = vl; }
    }

    static class IntOrdMng implements OrdMng {
        Qn hd = null, tl = null;
        int sz = 0;

        @Override
        public int ict(String a) {
            var b = hd;
            var c = 0;
            while (b != null) {
                for (var d : b.vl.ar())
                    if (d.nm.equals(a))
                        c++;
                b = b.nx;
            }
            return c;
        }

        @Override public int ict(MnuItm a) { return ict(a.nm); }

        @Override
        public Ord[] ors() {
            var a = hd;
            var b = new ArrayList<Ord>();
            while (a != null) {
                b.add(a.vl);
                a = a.nx;
            }
            return b.toArray(new Ord[0]);
        }

        @Override
        public int smc() {
            var a = hd;
            var b = 0;
            while (a != null) {
                b += a.vl.smc();
                a = a.nx;
            }
            return b;
        }

        @Override public int oct() { return sz; }

        boolean dd(Ord a) {
            sz++;

            if (hd == null) {
                hd = new Qn(a);
                tl = hd;
                return true;
            }

            var b = new Qn(a);
            tl = b;

            var c = hd;
            while (c != null) {
                if (c.nx == null) {
                    c.nx = b;
                    b.pr = c;
                    c = null;
                } else
                    c = c.nx;
            }

            return true;
        }

        Ord rm() {
            var a = tl.nx;
            var b = tl.pr;

            b.nx = a;
            a.pr = b;

            sz--;
            return a.vl;
        }

        Ord or() { return tl.vl; }
    }

    static class TblOrdMng implements OrdMng {
        Ord[] ors = new Ord[0];

        @Override
        public int ict(String a) {
            var d = 0;
            for (var b : ors)
                for (var c : b.ar())
                    if (c.nm.equals(a))
                        d++;
            return d;
        }

        @Override public int ict(MnuItm a) { return ict(a.nm); }
        @Override public Ord[] ors() { return ors; }

        @Override
        public int smc() {
            var a = 0;
            for (var b : ors)
                a += b.smc();
            return a;
        }

        @Override public int oct() { return ors.length; }

        void dd(Ord a, int b) {
            if (b >= ors.length) {
                var c = new Ord[b + 1];
                arraycopy(ors, 0, c, 0, ors.length);
                ors = c;
                ors[ors.length - 1] = a;
            } else
                ors[b] = a;
        }

        Ord gt(int a) { return ors[a]; }
        void ddd(MnuItm a, int b) { ors[b].dd(a); }

        void rm(int a) {
            var b = new Ord[ors.length - 1];
            arraycopy(ors, 0, b, 0, a);
            arraycopy(ors, a + 1, b, a, ors.length - 1 - a);
            ors = b;
        }

        int rm(Ord a) {
            for (var d = 0; d < ors.length; d++)
                if (ors[d].equals(a)) {
                    rm(d);
                    return d;
                }
            return -1;
        }

        int rma(Ord a) {
            var c = 0;
            for (var d = 0; d < ors.length; d++)
                if (ors[d].equals(a)) {
                    c++;
                    rm(d);
                }
            return c;
        }

        int ftn() {
            var a = 0;
            for (var i : ors)
                if (i != null) a++;
            return a;
        }

        int[] fre() {
            var a = new int[0];
            var j = 0;
            for (var i : ors) {
                if (i == null) {
                    var b = new int[a.length + 1];
                    arraycopy(a, 0, b, 0, a.length);
                    a = b;
                    a[a.length - 1] = j;
                }
                j++;
            }
            return a;
        }

        Ord[] gte() { return ors; }
    }
}
