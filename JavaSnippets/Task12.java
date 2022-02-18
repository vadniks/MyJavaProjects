import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Task12 {

    static class Person {
        String ln, nm, tn;

        Person(String _ln)
        { ln = _ln; }

        String fll() {
            return ln + (nm != null ? ' ' + nm : "") + (tn != null ? ' ' + tn : "");
        }
    }

    static class Adress {
        String cntr, rgn, ct, strt, hs, crps, flt;

        @Override
        public String toString() {
            return cntr + ' ' + rgn + ' ' + ct + ' ' + strt + ' ' + hs + ' ' + crps + ' ' + flt;
        }

        private static int mkAdr(Adress b, int c, String a) {
            if (c > 0)
                a = a.substring(1);

            switch (c) {
                case 0 -> b.cntr = a;
                case 1 -> b.rgn = a;
                case 2 -> b.ct = a;
                case 3 -> b.strt = a;
                case 4 -> b.hs = a;
                case 5 -> b.crps = a;
                case 6 -> b.flt = a;
            }
            c++;
            return c;
        }

        static Adress frmta(String s, char d) {
            var t = s.split(String.valueOf(d));
            var b = new Adress();

            var c = 0;
            while (c < t.length) {
                var a = t[c];
                c = mkAdr(b, c, a);
            }
            return b;
        }

        static Adress frmtb(String s, char d) {
            var t = new StringTokenizer(s, String.valueOf(d));
            var b = new Adress();

            var c = 0;
            while (t.hasMoreTokens()) {
                var a = t.nextToken();
                c = mkAdr(b, c, a);
            }
            return b;
        }
    }

    static class Shirt {
        String a, b, c, d;

        @Override
        public String toString() {
            return a + '_' + b + '_' + c + '_' + d;
        }

        static Shirt[] frmt(String[] e) {
            var h = new Shirt();
            var k = new Shirt[e.length];
            var l = 0;
            for (var j : e) {
                var f = new StringTokenizer(j, ",");
                var g = 0;

                while (f.hasMoreTokens()) {
                    var i = f.nextToken();
                    switch (g) {
                        case 0 -> h.a = i;
                        case 1 -> h.b = i;
                        case 2 -> h.c = i;
                        case 3 -> h.d = i;
                    }
                    g++;
                }
                k[l] = h;
                h = new Shirt();
                l++;
            }
            return k;
        }
    }

    static String frmt(String a) {
        var b = a.charAt(0) == '8';

        var c = new StringBuilder();
        c.append("+").append(b ? '7' : a.charAt(1));

        var d = a.substring(b ? 1 : 2);
        for (int i = 0, j = 0, k = 0; i < 3 && j < d.length();) {
            if (i < 2 ? k == 3 : k == 4) {
                c.append('-');
                i++;
                k = 0;
            } else {
                c.append(d.charAt(j));
                j++;
                k++;
            }
        }
        return c.toString();
    }

    // word1 tra aword 1st brw dworb
    static String fl(String a) throws IOException {
        var b = new File(a);
        var c = new char[64];
        new FileReader(b).read(c);
        var d = new String(c).split("\n")[0].split(" ");

        for (int i = 0; i < d.length - 1; i++) {
            for (int j = 0; j < d.length; j++) {
                var e = d[i].trim();
                var f = d[j].trim();

                if (e.equals(f))
                    continue;

                if (Character.toLowerCase(f.charAt(f.length() - 1)) ==
                        Character.toLowerCase(e.charAt(0))) {
                    d[i] = f;
                    d[j] = e;
                }
            }
        }
        return Arrays.toString(d);
    }

    public static void main(String[] args) {
        var a = Adress.frmta("Country, Region, City, Street, House, Corpus, Flat", ',');
        System.out.println(a);
        a = Adress.frmtb("Country, Region, City, Street, House, Corpus, Flat", ',');
        System.out.println(a);

        String[] b = {
                "S001,Black Polo Shirt,Black,XL",
                "S002,Black Polo Shirt,Black,L",
                "S003,Blue Polo Shirt,Blue,XL",
                "S004,Blue Polo Shirt,Blue,M",
                "S005,Tan Polo Shirt,Tan,XL",
                "S006,Black T-Shirt,Black,XL",
                "S007,White T-Shirt,White,XL",
                "S008,White T-Shirt,White,L",
                "S009,Green T-Shirt,Green,S",
                "S010,Orange T-Shirt,Orange,S",
                "S011,Maroon Polo Shirt,Maroon,S"
        };
        var c = Shirt.frmt(b);
        System.out.println(Arrays.toString(c));

        System.out.println(frmt("+79175655655"));
        System.out.println(frmt("89175655655"));

        try { System.out.println(fl("/home/admin/IdeaProjects/JavaTasks/res/b.b.txt")); }
        catch (IOException ignored) {}
    }
}
