import static java.lang.Math.pow;

public class Task5 {

    public static void main(String[] args) {
//        t1(1, 0, 7); //*
//        t2(1, 10); //*
//        t3(10, -5, 1); //*
//        t3(5, 1, 5); //*
//        t4_(4, 18);
//        System.out.println(t4(3, 11, 0, 0, 0));
//        t5(123456, 0); //*
//        System.out.println(t6(11, 2)); //*
//        System.out.println(t6(15, 2)); //*
        t7(21, 1, -1);
    }

    // i - current digit, k - current digit index, n - amount fo numbers to print
    static void t1(int i/*= 1*/, int k/*= 0*/, final int n) {
        for (int j = 0; j < i && k < n; j++, k++)
            System.out.print(i + " ");

        if (i <= n)
            t1(++i, k, n);
    }

    // i - current number, n - amount fo numbers to print
    static void t2(int i/*= 1*/, final int n) {
        System.out.print(i + " ");
        if (i < n)
            t2(++i, n);
    }

    // a - a, b.b - b.b, i - current number
    static void t3(final int a, final int b, int i/*= a or b.b*/) {
        System.out.print(i + " ");
        if (a < b && i < b)
            t3(a, b, ++i);
        else if (a > b && i > b)
            t3(a, b, --i);
    }

    static void t4_(final int k, final int s) {
        int m = 0;
        for (int j = (int) pow(10, k - 1); j < (int) pow(10, k); j++) {
            int l = 0, n = j;
            for (int i = 1; i <= k; i++) {
                l += n % 10;
                n /= 10;
            }

            if (l == s) {
//                System.out.println(j);
                m++;
            }
        }
        System.out.println(m);
    }

    // k - k, s - s,
    static int t4(final int k, final int s, int l/*= 0*/, int i/*= 0*/, int m/*= 0*/) {
        if (i > 0) {
            m += l % 10;
            return i < k ? t4(k, s, l / 10, ++i, m) : m;
        } else {
            int n = 0;
            for (int j = (int) pow(10, k - 1); j < (int) pow(10, k); j++) {
                if (t4(k, s, j, 1, 0) == s) {
//                    System.out.println(j);
                    n++;
                }
            }
            return n;
        }
    }

    // n - given number, s - current sum
    static void t5(int n, int s/*= 0*/) {
        s += n % 10;
        n /= 10;

        if (n != 0)
            t5(n, s);
        else
            System.out.println(s);
    }

    // n - number, i - divider
    static boolean t6(final int n, int i/*= 2*/) {
        if (n <= 2)
            return true;
        else if (n % i == 0)
            return false;
        else if (i * i > n)
            return true;
        else
            return t6(n, ++i);
    }

    // n - number, i - divider, j - flag and
    static int t7(final int n, int i/*= 1*/, int j/*= -1*/) {
        if (n % i == 0) {
            if (j < 0 && t7(i, 1, 0) <= 2)
                System.out.println(i);
            else
                j++;
        }

        if (i <= n)
            return t7(n, ++i, j);
        else
            return j;
    }
}
