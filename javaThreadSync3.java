import java.lang.Thread;
import java.util.concurrent.atomic.AtomicIntegerArray;

/*
 * Gonna produce the following output every time:
 * a 0
 * b 0123
 * c 0123456
 * a 0
 * b 0123
 * c 0123456
 * a 0
 * b 0123
 * c 0123456
 */
public class A {
    private int d = 0;
    private AtomicIntegerArray e = new AtomicIntegerArray(3);

    static <a> void pr(final a b) { System.out.print(b); }
    
    static void fr(final int a) { for (
        int b = 0, c = a + a * 2; 
        b <= c; 
        pr(b++)
    ); }
    
    void wt(final int a) { try { 
        while (d != a) 
            wait();
    } catch(final Exception $) {} }

    void cl(final int i) {
        var $ = 0;
        while (($ = e.get(i)) < 3) { 
            switch (i) {
                case 0 -> d(0, 'a', 1); 
                case 1 -> d(1, 'b', 2);
                case 2 -> d(2, 'c', 0);
            }
            e.set(i, $ + 1);
        }
    }

    public static void main(final String... $$) {
        final var $ = new A();
    	
    	final var a = new Thread(() -> $.cl(0));
    	final var b = new Thread(() -> $.cl(1));
    	final var c = new Thread(() -> $.cl(2));
    	
    	a.start();
    	b.start();
    	c.start();
    }
    
    synchronized void d(final int i, final char j, final int k) {
        wt(i);
        pr("\n" + j + ' ');
        fr(i);
        d = k;
        notifyAll();
    }
}
