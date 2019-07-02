import java.lang.Thread;
import java.lang.InterruptedException;

/**
 * @author Vad Nik
 * @version dated Jul 01, 2019.
 * @link https://github.com/vadniks
 *
 * Thread syncing in java.
 * Console output is 'abc' 3 times.
 */
class JavaTheadSync2 {
    private volatile char state = 'a';

    public static void main(String[] args) {
        JavaTheadSync2 jts = new JavaTheadSync2();
	Thread aa = new Thread(jts::a);
	Thread bb = new Thread(jts::b);
	Thread cc = new Thread(jts::c);
	aa.start();
	bb.start();
	cc.start();
    }

    private synchronized void a() {
	try {
            for (int i = 0; i < 4; i++) {
	        while (state != 'a')
		    wait();
	        state = 'b';
                System.out.println("a");
	        notifyAll();
            }
	} catch (InterruptedException ignored) {}
    }

    private synchronized void b() {
	try {
	    for (int i = 0; i < 4; i++) {
	        while (state != 'b')
		    wait();
	        state = 'c';
                System.out.println("b");
	        notifyAll();
            }
	} catch (InterruptedException ignored) {}
    }

    private synchronized void c() {
	try {
	    for (int i = 0; i < 4; i++) {
	        while (state != 'c')
		    wait();
	        state = 'a';
                System.out.println("c");
	        notifyAll();
            }
	} catch (InterruptedException ignored) {}
    }	
}
