
import java.io.File;
import java.util.*;

public class Task25_26 {

    static <T> void p(T a) { System.out.println(a); }

    public static void main(String[] args) {
        for (var a : t1_11(new String[]{ "a", "b", "c", "d", "e" }))
            p(a);

        p("");
        var a = new T2_11<String>();
        a.b("a", 0);
        p(a.a(0));

        p("\n" + t3_11(new Integer[]{ 1, 2, 3, 4, 5 }, 0));

        p("");
        t4_11();

        var b = new t1_12<Long>();
        p("");
        for (var c : b.nal(new Long[]{ 1L, 2L, 3L, 4L, 5L }))
            p(c);
        p("");
        for (var c : b.nhs(new Long[]{ 1L, 2L, 3L, 4L, 5L }))
            p(c);
        p("");
        var c = b.nhm(
                new Long[]{ 1L, 2L, 3L, 4L, 5L },
                new Float[]{ 1f, 2f, 3f, 4f, 5f });
        p(c.get(1L));
        p(c.get(2L));
        p(c.get(3L));
        p(c.get(4L));
        p(c.get(5L));
    }

    static <T> List<T> t1_11(T[] a)
    { return Arrays.asList(a); }

    static class T2_11<T> {
        private final Object[] a = new Object[1];

        @SuppressWarnings("unchecked")
        T a(int b) { return (T) a[b]; }

        void b(T b, int c) { a[c] = b; }
    }

    static <T> T t3_11(T[] a, int b) {
        if (b < 0 || b >= a.length)
            throw new IllegalArgumentException();
        return a[b];
    }

    static void t4_11() {
        var b = 0;
        for (var a : Objects.requireNonNull(new File("/").list())) {
            if (b == 5) break;;
            p(a);
            b++;
        }
    }

    static class t1_12<T> {

        ArrayList<T> nal(T[] a) { return new ArrayList<>(Arrays.asList(a)); }
        HashSet<T> nhs(T[] a) { return new HashSet<>(Arrays.asList(a)); }

        <K, V> HashMap<K, V> nhm(K[] a, V[] b) {
            if (a == null || b == null || a.length != b.length)
                throw new IllegalArgumentException();

            var c = new HashMap<K, V>();
            for (var d = 0; d < a.length; d++)
                c.put(a[d], b[d]);

            return c;
        }
    }
}
