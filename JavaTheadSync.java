
import java.lang.Thread;
import java.lang.InterruptedException;

/**
 * @author Vad Nik
 * @version dated Jul 01, 2019.
 * @link https://github.com/vadniks
 *
 * Thread syncing in java.
 * Console output is 'abc'.
 */
class JavaTheadSync {
    private volatile char state = 'a';

    public static void main(String[] args) {
        JavaTheadSync jts = new JavaTheadSync();
	Thread aa = new Thread(jts::a);
	Thread bb = new Thread(jts::b);
	Thread cc = new Thread(jts::c);
	aa.start();
	bb.start();
	cc.start();
    }

    private synchronized void a() {
	try {
	    while (state != 'a')
		wait();
	    System.out.println("a");
	    state = 'b';
	    notify();
	} catch (InterruptedException ignored) {}
    }

    private synchronized void b() {
	try {
	    while (state != 'b')
                wait();
	    System.out.println("b");
	    state = 'c';
	    notify();
	} catch (InterruptedException ignored) {}
    }

    private synchronized void c() {
	try {
	    while (state != 'c')
		wait();
	    System.out.println("c");
	    state = 'a';
	    notify();
	} catch (InterruptedException ignored) {}
    }	
}
