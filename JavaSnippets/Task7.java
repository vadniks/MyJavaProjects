import java.util.Arrays;
import java.util.Scanner;

public class Task7 {

    public static void main(String[] args) {
        new Rnr();
    }

    static class Stk {
        int[] arr;
        int sz = -1;

        Stk()
        { arr = new int[1]; }

        Stk(int ln)
        { arr = new int[ln]; }

        private void gr() {
            if (++sz < arr.length)
                return;

            int[] ar = new int[arr.length + 1];
            System.arraycopy(arr, 0, ar, 0, arr.length);
            arr = ar;
        }

        void ps(int a) {
            gr();
            arr[sz] = a;
        }

        void pb(int a) {
            gr();

            if (arr.length - 1 >= 0)
                System.arraycopy(arr, 0, arr, 1, arr.length - 1);

            arr[0] = a;
        }

        int pp() {
            if (em())
                return -1;

            int a = arr[sz--];
            int[] b = new int[arr.length - 1];
            System.arraycopy(arr, 0, b, 0, arr.length - 1);
            arr = b;
            return a;
        }

        int pf() {
            if (em())
                return -1;

            int a = arr[0];
            sz--;

            int[] b = new int[arr.length - 1];
            System.arraycopy(arr, 1, b, 0, arr.length - 1);
            arr = b;

            return a;
        }

        boolean em()
        { return sz == -1; }
    }

    static class Rnr {
        final int ln = 10;
        final int lg = ln / 2;
        final int mx = 106;
        Stk pl1 = new Stk(lg);
        Stk pl2 = new Stk(lg);
        int sps = 0;
        final String fr = "first";
        final String sn = "second";
        final int lw = 0;
        final int hg = ln - 1;
        final String bt = "botva";

        @Deprecated
        final int[] tpla = { 2, 4, 2, 4, 2 };
        @Deprecated
        final int[] tplb = { 4, 2, 4, 2, 4 };

        Rnr()
        { pl1.arr = tpla; pl1.sz = tpla.length - 1; pl2.arr = tplb; pl2.sz = tplb.length - 1; ccl(); } //prs(); }

        void prs() {
            Scanner sc = new Scanner(System.in);

            for (int j = 0, l; j < 2; j++) {
                for (String i : sc.nextLine().split(" ")) {
                    l = Integer.parseInt(i);

                    if (j == 0) pl1.ps(l); else pl2.ps(l);
                }
            }
            ccl();
        }

        /*
        1 3 5 7 9
        2 4 6 8 0

        1 < 2

        3 5 7 9
        4 6 8 0 1 2

        3 < 4

        5 7 9
        6 8 0 1 2 3 4

        5 < 6

        7 9
        8 0 1 2 3 4 5 6

        7 < 8

        9
        0 1 2 3 4 5 6 7 8

        9 > 0 (<!)

        -
        1 2 3 4 5 6 7 8 9 0
        */

        void ccl() {
            int a, b;
            System.out.println("_ " + Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
            for (sps = 1; sps <= mx; sps++) {
//                System.out.println("| " + Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
                a = pl1.pf();
                b = pl2.pf();
                System.out.println("| " + Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr) + ' ' + a + ' ' + b);
                if (a == lw && b == hg) {
                    pl1.ps(a);
                    pl1.ps(b);
                } else if (a == hg && b == lw) { // a=9 b.b=0
                    pl2.ps(a);
                    pl2.ps(b);
                } else if (a > b) {
                    pl1.ps(a);
                    pl1.ps(b);
                } else {
                    pl2.ps(a);
                    pl2.ps(b);
                }
//                System.out.println("| " + Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
                if (pl1.em()) {
                    System.out.println(sn + ' ' + sps);
                    System.out.println(Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
                    return;
                } else if (pl2.em()) {
                    System.out.println(fr + ' ' + sps);
                    System.out.println(Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
                    return;
                }
                System.out.println("/ " + Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
            }
            System.out.println(bt);
            System.out.println(Arrays.toString(pl1.arr) + ' ' + Arrays.toString(pl2.arr));
        }

        @Deprecated(forRemoval = true)
        String wrk(int[] a) {
            String b = "";
            Stk ar = new Stk();

            for (int i : a) {
                if (i > 0) {
                    ar.ps(i);
                    b += ", 0";
                } else if (i == 0) {
                    if (ar.em())
                        b += "-1";
                    else
                        b += ", " + ar.pp();
                }
            }
            return b;
        }
    }
}

