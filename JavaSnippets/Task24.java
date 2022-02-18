import java.util.Scanner;
import java.util.function.Function;

public class Task24 {

    static <T> void p(T a) { System.out.println(a); }

    public static void main(String[] args) {
        t1();
        t2_3_4();
    }

    static void t1() {
        try {p(2 / 0); }
        catch (Exception ignored) {}
    }

    static void t2_3_4() {
        Scanner a = new Scanner(System.in);
        p("Enter an integer ");
        int i = Integer.parseInt(a.nextLine());

        try { p(2 / i); }
        catch (ArithmeticException ignored) { p("arithmetic"); }
        catch (Exception ignored) { p("exception"); }
        finally { p("finally"); }
    }

    static void t5() {
        Function<String, String> b = a -> {
            if (a == null) throw new NullPointerException();
            return a;
        };

        try { b.apply(null); }
        catch (NullPointerException ignored) { p("null"); }
    }

    static void t6() {
        Function<String, String> b = a -> {
            if (a == null) throw new NullPointerException();
            return "a: " + a;
        };

        try { p(b.apply(null)); }
        catch (Exception ignored) { p("a: null"); }
    }

    static String t7_a(String a) throws NullPointerException {
        if (a == null) throw new NullPointerException();
        return "a: " + a;
    }

    static void t7_b(String a) {
        try { p(t7_a(a)); }
        catch (Exception ignored) { p("a: exception"); }
    }

    static void t7() {
        var c = new Scanner(System.in);
        t7_b(c.nextLine());
    }

    static String t8_a(String a) throws NullPointerException { return t7_a(a); }

    static void t8_b(String a) throws NullPointerException { p(t8_a(a)); }

    static void t8() {
        var c = new Scanner(System.in);
        try { t7_b(c.nextLine()); }
        catch (Exception ignored) {
            p("a: exception");
            t8();
        }
    }
}
