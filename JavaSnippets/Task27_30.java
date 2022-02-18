
import java.util.regex.Pattern;

public class Task27_30 {

    static <T> void p(T a) { System.out.println(a); }

    public static void main(String[] args) {
        t1_27("0000@1111@@2222@@@3333");
        p("");
        t2_27("abcdefghijklmnopqrstuv18340");
        t2_27("abcdefghijklmnoasdfasdpqrstuv18340");
        p("");
        t1_28("25.98 USD, 44 EU, 0.004 RUB");
        t1_28("44 ERR, 0,004 ");
        p("");
        t2_28("(1 + 8) – 9 / 4");
        t2_28("6 / 5 – 2 * 9");
        p("");
        t1_29("29/02/2000");
        t1_29("29/02/1800");
        p("");
        t2_29("user@example.com");
        t2_29("root@localhost"); // the standard says it's not valid, but the task says otherwise
        t2_29("myhost@@@com.ru");
        t2_29("@my.ru");
        t2_29("Julia String");
        p("");
        t1_30("F032_Password");
        t1_30("TrySpy10");
        t1_30("smart_pass");
        t1_30("A007");
    }

    static void mtch(String a, String b, boolean c) {
        var d = Pattern.compile(a);
        var e = d.matcher(b);
        p(!c ? e.matches() : e.find());
    }

    static void t1_27(String a) {
        for (var b : a.split("@+"))
            p(b);
    }

    static void t2_27(String a)
    { p(Pattern.compile("^[a-v]{22}(\\d){5}$").matcher(a).matches()); }

    static void t1_28(String a) {
        var b = Pattern.compile("\\d+(.\\d+)? (USD|RUB|EU)");
        for (var c : b.matcher(a).results().toList())
            p("| " + c.group());
    }

    static void t2_28(String a) {
        var b = Pattern.compile("\\d( ?)\\+");
        p(b.matcher(a).find());
    }

    static void t1_29(String a) {
        var b = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/((1(?![0-8])\\d{3})|([2-9]\\d{3}))$");
        p(b.matcher(a).matches());
    }

    static void t2_29(String a) {
        var b = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        p(b.matcher(a).matches());
    }

    static void t1_30(String a) {
        var b = Pattern.compile("(?=.*[a-z_])(?=.*[A-Z])(?=.*[0-9]).{8,}");
        p(b.matcher(a).find());
    }
}
