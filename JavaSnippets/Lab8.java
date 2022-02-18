import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Lab8 {
    static File f = new File("/home/admin/IdeaProjects/JavaTasks/res/a.txt");

    public static void main(String[] args) {
        t1(false);
        t2();
        t3();
        t4();
    }

    static void t1(boolean a) {
        try(var wr = new FileWriter(f, a)) {
            wr.write(new Scanner(System.in).nextLine());
        } catch (IOException ignored) {}
    }

    static void t2() {
        try(var rd = new FileReader(f)) {
            var a = new char[256];
            rd.read(a);
            System.out.println(new String(a));
        } catch (IOException ignored) {}
    }

    static void t3()
    { t1(false); }

    static void t4()
    { t1(true); }
}
