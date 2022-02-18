
import java.util.HashMap;

import static java.lang.System.arraycopy;

public class Task14_16 {

    static <T> void p(T a) { System.out.println(a); }

    public static void main(String[] args) {
        var a = new Ord();

        a.dd(new Drk("drk1", "null"));
        a.dd(new Drk("drk2", "null"));
        for (var i : a.drs) p(i);
        a.rm(new Drk("drk1", "null"));
        p("");
        for (var i : a.drs) p(i);

        p("");

        a.dd(new Dsh("dsh1", "null"));
        a.dd(new Dsh("dsh2", "null"));
        for (var i : a.dss) p(i);
        a.rm(new Dsh("dsh2", "null"));
        p("");
        for (var i : a.dss) p(i);

        p("\n");

        t3();
        t5();
    }

    interface Itm {
        int gCst();
        String gNm();
        String gDsc();
        void sCst(int a);
        void sNm(String a);
        void sDsc(String a);
    }

    static abstract class AbsItm implements Itm {
        final int cst;
        final String nm, dsc;
        static final int und = -1;

        AbsItm(String nm, String dsc)
        { this(und, nm, dsc); }

        AbsItm(int cst, String nm, String dsc)
        { this.cst = cst; this.nm = nm; this.dsc = dsc; }

        @Override public int gCst() { return cst; }
        @Override public String gNm() { return nm; }
        @Override public String gDsc() { return dsc; }

        @Override public void sCst(int a) { throw new UnsupportedOperationException(); }
        @Override public void sNm(String a) { throw new UnsupportedOperationException(); }
        @Override public void sDsc(String a) { throw new UnsupportedOperationException(); }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbsItm absItm = (AbsItm) o;
            return cst == absItm.cst && nm.equals(absItm.nm) && dsc.equals(absItm.dsc);
        }

        @Override public String toString()
        { return getClass().getSimpleName() + ' ' + cst + ' ' + nm + ' ' + dsc; }
    }

    static class Drk extends AbsItm {

        Drk(String nm, String dsc) {
            super(nm, dsc);
            if (nm == null || dsc == null)
                throw new IllegalArgumentException();
        }

        Drk(int cst, String nm, String dsc) {
            super(cst, nm, dsc);
            if (cst < 0 || nm == null || dsc == null)
                throw new IllegalArgumentException();
        }
    }

    static class Dsh extends AbsItm {

        Dsh(String nm, String dsc)
        { super(nm, dsc); }

        Dsh(int cst, String nm, String dsc)
        { super(cst, nm, dsc); }
    }

    static class Ord {
        Drk[] drs = new Drk[0];
        Dsh[] dss = new Dsh[0];

        void dd(Drk a) {
            var b = new Drk[drs.length + 1];
            arraycopy(drs, 0, b, 0, drs.length);
            b[b.length - 1] = a;
            drs = b;
        }

        void rm(Drk a) {
            var b = new Drk[drs.length - 1];
            for (int i = 0, j = 0; i < drs.length; i++) {
                var c = drs[i];

                if (c.equals(a))
                    continue;

                b[j] = c;
                j++;
            }
            drs = b;
        }

        void dd(Dsh a) {
            var b = new Dsh[dss.length + 1];
            arraycopy(dss, 0, b, 0, dss.length);
            b[b.length - 1] = a;
            dss = b;
        }

        void rm(Dsh a) {
            var b = new Dsh[dss.length - 1];
            for (int i = 0, j = 0; i < dss.length; i++) {
                var c = dss[i];

                if (c.equals(a))
                    continue;

                b[j] = c;
                j++;
            }
            dss = b;
        }
    }

    static class TblOrdMng {
        Ord[] ords = new Ord[0];

        void dd(Ord a, int b) {
            if (b >= ords.length) {
                var c = new Ord[b + 1];
                arraycopy(ords, 0, c, 0, ords.length);
                ords = c;
                ords[ords.length - 1] = a;
            } else
                ords[b] = a;
        }

        Ord gt(int a) {
            if (ords.length <= a || a < 0 || ords[a] == null)
                throw new IlgTblNmbr();
            return ords[a];
        }

        void ddd(Dsh a, int b) {
            if (b >= ords.length || b < 0 || ords[b] == null)
                throw new IlgTblNmbr();
            ords[b].dd(a);
        }

        void rm(int a) {
            if (a >= ords.length || a < 0 || ords[a] == null)
                throw new IlgTblNmbr();
            ords[a] = null;
        }

        int ftn() {
            var a = 0;
            for (var i : ords)
                if (i != null) a++;
            return a;
        }

        int[] fre() {
            var a = new int[0];
            var j = 0;
            for (var i : ords) {
                if (i == null) {
                    var b = new int[a.length + 1];
                    arraycopy(a, 0, b, 0, a.length);
                    a = b;
                    a[a.length - 1] = j;
                }
                j++;
            }
            return a.length > 0 ? a : null;
        }

        Ord[] gte() { return ords; }

        int ocs() {
            var a = 0;
            for (var i : ords) {
                for (var j : i.dss)
                    a += j.cst;
            }
            return a;
        }

        int dqt(String a) {
            var b = 0;
            for (var i : ords) {
                for (var j : i.dss) {
                    if (j.nm.equals(a))
                        b++;
                }
            }
            return b;
        }
    }

    static void t3() {
        var a = new IntOrd(3);

        a.dd(new Drk(2, "2", "null"));
        a.dd(new Drk(1, "1", "null"));
        a.dd(new Drk(2, "2", "null"));
        a.dd(new Drk(4, "4", "null"));
        a.dd(new Drk(2, "2", "null"));
        a.pr();

        p("------");
//        a.rm("2");
        a.rma("2");
        a.pr();

        p("------");
        a.dd(new Dsh(3, "3", "smth"));
        a.dd(new Dsh(2, "2", "smth"));
        a.dd(new Dsh(2, "2", "smth"));
        a.dd(new Dsh(2, "2", "smth"));
        a.dd(new Dsh(3, "3", "smth"));
        for (var i : a.ar())
            p(i);

        p("\n" + a.smc());

        p("\n" + a.cnt("2"));

        p("");
        for (var i : a.aru())
            p(i);

        p("-------");
        for (var i : a.aro())
            p(i);
    }

    static class IntOrd implements IOrd {
        Nd rt, ls;
        int sz;

        IntOrd() { rt = null; ls = null; sz = 0; }

        IntOrd(@SuppressWarnings("unused") int a) { this(); }

        @Override public void pr() {
            var a = rt;
            while (a != null) {
                p(a.vl);
                a = a.nx;
            }
        }

        @Override public boolean dd(Itm a) {
            sz++;

            if (rt == null) {
                rt = new Nd(a);
                ls = rt;
                return true;
            }

//            if (rt.vl == null && a != null) {
//                var b.b = rt;
//                rt = new Nd(a);
//                rt.nx = b.b.nx;
//                return true;
//            }

            var b = rt;
            while (b.nx != null/* && b.b.nx.vl != null*/)
                b = b.nx;

//            var c = b.b.nx;
            b.nx = new Nd(a);
            b.nx.pr = b;
//            b.b.nx.nx = c;

            ls = b.nx;
            return true;
        }

        @Override public boolean rm(String a) {
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

        @Override public int rma(String a) {
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

        @Override public int sz() { return sz; }

        @Override public Itm[] ar() {
            var a = rt;
            var b = new Itm[0];

            while (a != null) {
                var c = new Itm[b.length + 1];
                arraycopy(b, 0, c, 0, b.length);
                b = c;

                b[b.length - 1] = a.vl;
                a = a.nx;
            }
            return b.length > 0 ? b : null;
        }

        @Override public int smc() {
            var a = rt;
            var b = 0;
            while (a != null) {
                b += a.vl.gCst();
                a = a.nx;
            }
            return b;
        }

        @Override public int cnt(String a) {
            var b = rt;
            var c = 0;
            while (b != null) {
                if (b.vl.gNm().equals(a))
                    c++;
                b = b.nx;
            }
            return c;
        }

        @Override public Itm[] aru() {
            var a = ar();
            var b = new Itm[0];
            var e = new boolean[a.length];

            for (var i = 0; i < a.length; i++) {
                var j = 0;
                for (; j < a.length; j++) {
                    if (i == j) continue;

                    if (a[i].gNm().equals(a[j].gNm()))
                        e[j] = true;
                }
                if (e[i]) continue;

                var d = new Itm[b.length + 1];
                arraycopy(b, 0, d, 0, b.length);
                b = d;

                b[b.length - 1] = a[i];
            }
            return b.length > 0 ? b : null;
        }

        @Override public Itm[] aro() {
            var a = ar();

            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    var b = a[i];
                    var c = a[j];

                    if (b.gNm().compareToIgnoreCase(c.gNm()) > 0) {
                        a[i] = c;
                        a[j] = b;
                    }
                }
            }
            return a;
        }

        static class Nd {
            final Itm vl; Nd nx = null, pr = null;
            Nd(Itm vl) { this.vl = vl; }
        }
    }

    interface IOrd {
        void pr();
        boolean dd(Itm a);
        boolean rm(String a);
        int rma(String a);
        int sz();
        Itm[] ar();
        int smc();
        int cnt(String a);
        Itm[] aru();
        Itm[] aro();
    }

    static class RstOrd extends Ord implements IOrd {
        private final IOrd dlg = new IntOrd();
        @Override public void pr() { dlg.pr(); }
        @Override public boolean dd(Itm a) { return dlg.dd(a); }
        @Override public boolean rm(String a) { return dlg.rm(a); }
        @Override public int rma(String a) { return dlg.rma(a); }
        @Override public int sz() { return dlg.sz(); }
        @Override public Itm[] ar() { return dlg.ar(); }
        @Override public int smc() { return dlg.smc(); }
        @Override public int cnt(String a) { return dlg.cnt(a); }
        @Override public Itm[] aru() { return dlg.aru(); }
        @Override public Itm[] aro() { return dlg.aro(); }
    }

    static void t5() {
        p("---------");

        var a = new OrdMng();
        var b = new IntOrd();

        b.dd(new Drk(1, "Drk1", "null"));
        b.dd(new Drk(2, "Drk2", "null"));
        a.dd("a", b);

        b = new IntOrd();
        b.dd(new Dsh(3, "Dsh3", "null"));
        b.dd(new Dsh(4, "Dsh4", "null"));
        a.dd("b", b);

        b = new IntOrd();
        b.dd(new Drk(5, "Drk5", "null"));
        b.dd(new Dsh(6, "Dsh6", "null"));
        a.dd("c", b);

        p("");
        for (var i : a.gt("a").ar())
            p("a: " + i);

        p("");
        for (var i : a.gt("b").ar())
            p("b.b: " + i);

        p("");
        for (var i : a.gt("c").ar())
            p("c: " + i);

        p("");
        a.rm("b");
        for (var i : a.hmp.values()) {
            for (var j : i.ar())
                p(j);
        }

        p("");
        a.ddd("a", new Drk(7, "Drk5", "null"));
        for (var i : a.gt("a").ar())
            p("a: " + i);

        p("");
        for (var i : a.avl()) {
            for (var j : i.ar())
                p(j);
        }

        p("\n" + a.smc());
        p("\n" + a.sum("Drk5"));
    }

    static class OrdMng extends TblOrdMng {
        HashMap<String, IOrd> hmp = new HashMap<>();

        void dd(String a, IOrd b) {
            if (hmp.containsKey(a))
                throw new IOrdAlrdAddException();
            hmp.put(a, b);
        }

        IOrd gt(String a) { return hmp.get(a); }

        void rm(String a) { hmp.remove(a); }

        void ddd(String a, Itm b) {
            var c = hmp.get(a);

            for (var i : c.ar())
                if (i.equals(b))
                    throw new IOrdAlrdAddException();

            c.dd(b);
        }

        IOrd[] avl() { return hmp.values().toArray(new IOrd[0]); }

        int smc() {
            var a = 0;
            for (var i : hmp.values())
                a += i.smc();
            return a;
        }

        int sum(String a) {
            var b = 0;
            for (var i : hmp.values())
                b += i.cnt(a);
            return b;
        }
    }

    static class IOrdAlrdAddException extends RuntimeException {}
    static class IlgTblNmbr extends RuntimeException {}
}
