import java.util.*;

public class Lab1 {

    public static void main(String[] args) {
        tsk1();
        tsk2(args);
        tsk3();
        tsk4();
        tsk5();
    }

    static void tsk1() {
        int[] a = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int b = 0, c = 0;

        for (b = 0; b < a.length; c += a[b], b++);
        System.out.println(c);

        b = 0;
        c = 0;
        while (b < a.length) {
            c += a[b];
            b++;
        }
        System.out.println(c);

        b = 0;
        c = 0;
        do {
            c += a[b];
            b++;
        } while (b < a.length);
        System.out.println(c);
    }

    static void tsk2(String[] a) {
        for (int i = 0; i < a.length; System.out.println(a[i]), i++);
    }

    static void tsk3() {
        for (int i = 1; i <= 10; System.out.print("" + (float) 1/i + " "), i++);
        System.out.println();
    }

    static void tsk4() {
        int[] a = new int[10];
        Random r = new Random(System.currentTimeMillis());
        int i = 0;

        for (; i < a.length; a[i] = r.nextInt(10), i++);
        System.out.println(Arrays.toString(a));

        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }

    static void tsk5() {
        final int a = 120;
        long b = 1;

        for (int i = 1; i <= a; i++)
            b *= i;

        System.out.println(b);
    }
}
