import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Task11Exp {

    public static void main(String[] args) {
        var a = new Sub(
                new Mul(new Cnst(2), new Var("x")),
                new Cnst(3)
        ).evl(new Pr<>("x", 5));
        System.out.println(a + "\n");

        a = new Add(new Sub(
                new Mul(new Var("x"), new Var("x")),
                new Mul(new Cnst(2), new Var("x"))
        ), new Cnst(1)).evl(new Pr<>("x", 5));
        System.out.println(a + "\n");

        a = new Add(
                new Mul(
                        new Mul(
                                new Var("x"),
                                new Sub(
                                        new Var("y"),
                                        new Cnst(2)
                                )
                        ),
                        new Var("z")
                ),
                new Cnst(1)
        ).evl(new Pr<>("x", 2), new Pr<>("y", 3), new Pr<>("z", 4));
        System.out.println(a + "\n");
    }

    static class Pr<A, B> {
        A a;
        B b;
        Pr(A _a, B _b) { a = _a; b = _b; }
    }

    interface Exp {
        Clc prc = Clc.nst;
        int clc();
        String nm();

        default int evl(Pr<String, Integer>... xs) {
            for (Pr<String, Integer> x : xs)
                prc.vrs.replace(x.a, x.b);

            var r = prc.rt.clc();
            prc.vrs.clear();
            return r;
        }

        default void nt(Exp a) {
            prc.rt = a;
        }

        final class Clc {
            HashMap<String, Integer> vrs = new HashMap<>();
            Exp rt = null;
            static final Clc nst = new Clc();

            private Clc() {}
        }
    }

    static class Cnst implements Exp {
        int b;

        Cnst(int _b) {
            b = _b;
            nt(this);
        }

        @Override
        public int clc() {
            return b;
        }

        @Override
        public String nm() {
            return /*"const_" +*/ String.valueOf(b);
        }
    }

    static class Var implements Exp {
        String nm;

        Var(String nm) {
            this.nm = nm;
            prc.vrs.put(nm, null);
            nt(this);
        }

        @Override
        public int clc() {
            return prc.vrs.get(nm);
        }

        @Override
        public String nm() {
            return /*"var_" +*/ nm;
        }
    }

    static abstract class Bin implements Exp {
        Exp aa, bb;
        String cnm;
        char sg;

        Bin(Exp a, Exp b, String cnm, char sg) {
            aa = a; bb = b;
            this.cnm = cnm;
            this.sg = sg;
            nt(this);
        }

        @Override
        public String nm() {
            return /*cnm +*/ '(' + aa.nm() + ' ' + sg + ' ' + bb.nm() + ')';
        }
    }

    static class Add extends Bin {

        Add(Exp a, Exp b) {
            super(a, b, "add", '+');
            nt(this);
        }

        @Override
        public int clc() {
            return aa.clc() + bb.clc();
        }
    }

    static class Sub extends Bin {

        Sub(Exp a, Exp b) {
            super(a, b, "sub", '-');
            nt(this);
        }

        @Override
        public int clc() {
            return aa.clc() - bb.clc();
        }
    }

    static class Mul extends Bin {

        Mul(Exp a, Exp b) {
            super(a, b, "mul", '*');
            nt(this);
        }

        @Override
        public int clc() {
            return aa.clc() * bb.clc();
        }
    }

    static class Div extends Bin {

        Div(Exp a, Exp b) {
            super(a, b, "div", '/');
            nt(this);
        }

        @Override
        public int clc() {
            return aa.clc() / bb.clc();
        }
    }
}
