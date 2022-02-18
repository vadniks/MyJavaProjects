import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Task7Stk {

    public static void main(String[] args) {
        new Rnr();
    }

    static class Rnr {
        final int ln = 10;
        final int lg = ln / 2;
        final int mx = 106;
        Stack<Integer> pl1 = new Stack<>();
        Stack<Integer> pl2 = new Stack<>();
        int sps = 0;
        final String fr = "first";
        final String sn = "second";
        final int lw = 0;
        final int hg = ln - 1;
        final String bt = "botva";

        Rnr() {
            /*pl1.add(1);
            pl1.add(3);
            pl1.add(5);
            pl1.add(7);
            pl1.add(9);

            pl2.add(2);
            pl2.add(4);
            pl2.add(6);
            pl2.add(8);
            pl2.add(0);

            ccl();*/
            prs();
        }

        void prs() {
            Scanner sc = new Scanner(System.in);

            for (int j = 0, l; j < 2; j++) {
                for (String i : sc.nextLine().split(" ")) {
                    l = Integer.parseInt(i);

                    if (j == 0) pl1.push(l); else pl2.push(l);
                }
                System.out.println("next:");
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
//            System.out.println(pl1.toString() + ' ' + pl2);
            for (sps = 1; sps <= mx; sps++) {

                a = pl1.firstElement();
                pl1.remove((Integer) a);

                b = pl2.firstElement();
                pl2.remove((Integer) b);

                if (a == lw && b == hg) {
                    pl1.push(a);
                    pl1.push(b);
                } else if (a == hg && b == lw) { // a=9 b.b=0
                    pl2.push(a);
                    pl2.push(b);
                } else if (a > b) {
                    pl1.push(a);
                    pl1.push(b);
                } else {
                    pl2.push(a);
                    pl2.push(b);
                }

                if (pl1.empty()) {
                    System.out.println(sn + ' ' + sps);
                    System.out.println(pl1.toString() + ' ' + pl2);
                    return;
                } else if (pl2.empty()) {
                    System.out.println(fr + ' ' + sps);
                    System.out.println(pl1.toString() + ' ' + pl2);
                    return;
                }
                System.out.println(pl1.toString() + ' ' + pl2);
            }
            System.out.println(bt);
            System.out.println(pl1.toString() + ' ' + pl2);
        }
    }
}
