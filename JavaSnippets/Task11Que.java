import java.util.Arrays;
import java.util.Iterator;

public class Task11Que {

    interface Que<T> extends Iterable<T> {
        void ps(T a);
        T fr();
        T pf();
        boolean em();
        void cl();
        int gsz();

        default void ps(T ...a) {
            for (var b : a)
                ps(b);
        }
    }

    static abstract class AbstractQueue<T> implements Que<T> {
        int sz = -1;

        @Override
        public int gsz() {
            return sz;
        }
    }

    static class QueArr<T> extends AbstractQueue<T> {
        Object[] arr;

        // invariant is that the size variable is left unchanged
        // after execution of this procedure
        // precondition - the array variable must be declared
        // postcondition - ends in any case and doesn't change the size variable
        QueArr() { cl(); }

        // invariant is that all array's elements are kept
        // in there before and after this function invoking
        // (no adding or deleting) and sequence of the elements
        // remains unchanged before after this function work
        // precondition - the array variable must be initialized
        // postcondition - ends in any case? always changes size
        // var and might change array var
        private void gr() {
            if (++sz < arr.length)
                return;

            var ar = new Object[arr.length + 1];
            System.arraycopy(arr, 0, ar, 0, arr.length);
            arr = ar;
        }

        // invariant is that the parameter is not being changed
        // precondition - same as in the gr fun
        // postcondition - same as in the gr fun
        // enqueue
        @Override
        public void ps(T a) {
            gr();
            arr[sz] = a;
        }

        // invariant is that the sequence of the elements
        // is not being changed while this function executes
        // precondition - same as in the ps(int) fun
        // postcondition - same as in the ps(int) fun
        @SafeVarargs @Override
        public final void ps(T... a) { super.ps(a); }

        // invariant is that nothing changes
        // precondition - array must be initialized
        // postcondition - ends in any case and changes nothing
        // element
        @SuppressWarnings("unchecked") @Override
        public T fr() { return (T) arr[0]; }

        // invariant is that the extracted element is left unchanged
        // before and after function's execution
        // precondition - array must be initialized
        // postcondition - ends in any case and might change all vars
        // dequeue
        @SuppressWarnings("unchecked")
        @Override
        public T pf() {
            if (em())
                return null;

            var a = arr[0];
            sz--;

            var b = new Object[arr.length - 1];
            System.arraycopy(arr, 1, b, 0, arr.length - 1);
            arr = b;

            return (T) a;
        }

        // invariant is that this function changes nothing
        // precondition - size var must be declared
        // postcondition - ends in any case and changes nothing
        // isEmpty
        @Override public final boolean em() { return sz == -1; }

        // invariant is that this function changes all
        // precondition - all vars must be declared
        // postcondition - ends in any case and doesn't change elements' values
        // clear
        @Override
        public void cl() {
            sz = -1;
            arr = new Object[1];
        }

        @Override
        public int gsz() {
            return sz;
        }

        // invariant is that array and the sequence of the elements are not being changed
        // precondition - array must be initialized
        // postcondition - ends in any case and doesn't change local environment
        @SuppressWarnings("unchecked") @Override
        public Iterator<T> iterator()
        { return (Iterator<T>) Arrays.stream(arr).iterator(); }
    }

    static class Queue2<T> extends QueArr<T> {
        private int ndx = -1;

        @Override
        public void ps(T a) {
            super.ps(a);
            ndx = 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T pf() {
            if (ndx == sz)
                ndx = 0;
            return (T) arr[ndx++];
        }
    }

    static class ArrayQueueModule<T> {
        final T a;
        public ArrayQueueModule(T a) { this.a = a; }
        @Override public String toString() { return "'" + a.toString() + "'"; }
    }

    static class ArrayQueueADT<T> implements Que<T> {
        private Que<T> q;

        ArrayQueueADT(Que<T> a) { q = a; }

        @Override
        public void ps(T a) { q.ps(a); }

        @Override
        public T fr() { return q.fr(); }

        @Override
        public T pf() { return q.pf(); }

        @Override
        public boolean em() { return q.em(); }

        @Override
        public void cl() { q.cl(); }

        @Override
        public Iterator<T> iterator() { return q.iterator(); }

        @Override
        public int gsz() {
            return q.gsz();
        }
    }

    static class ArrayQueue<T> implements Que<T> {
        private final Que<T> q = new Queue2<>();

        @Override
        public void ps(T a) { q.ps(a); }

        @Override
        public T fr() { return q.fr(); }

        @Override
        public T pf() { return q.pf(); }

        @Override
        public boolean em() { return q.em(); }

        @Override
        public void cl() { q.cl(); }

        @Override
        public Iterator<T> iterator() { return q.iterator(); }

        @Override
        public int gsz() {
            return q.gsz();
        }
    }

    public static void main(String[] args) {
        var a = new QueArr<Integer>();
        a.ps(1, 2, 3, 4, 5);
        for (var ignored : a)
            System.out.println(a.pf());

        System.out.println();

        tst((Integer b) -> b, (Void ignored) -> new ArrayQueueADT<>(new Queue2<>()));
        System.out.println();
        tst((Integer b) -> new ArrayQueueModule<>(String.valueOf(b)),
            (Void ignored) -> new ArrayQueue<>());

        System.out.println();

        var c = new QueLnk<Integer>();
        c.ps(1, 2, 3, 4, 5);
        for (var ignored : c)
            System.out.println(c.pf());
    }

    @FunctionalInterface interface Lmb<R, P> { R nw(P a); }

    static
    <T, C extends Que<T>>
    void tst(Lmb<T, Integer> a, Lmb<C, Void> c) {
        var b = c.nw(null);

        for (int i = 0; i <= 5; i++)
            b.ps(a.nw(i));

        for (int i = 0; i < b.gsz() * 2 + 2; i++)
            System.out.println(b.pf());
    }

    static class QueLnk<T> extends AbstractQueue<T> implements Iterator<T> {
        nd<T> rt, ls;

        private static class nd<T> {
            T vl; nd<T> nx;
            public nd(T _vl) { vl = _vl; nx = null; }
        }

        QueLnk() { sz = 0; }

        @Override
        public void ps(T a) {
            sz++;
            if (rt == null) {
                rt = new nd<>(a);
                ls = rt;
                return;
            }
            var b = new nd<>(a);
            ls.nx = b;
            ls = b;
        }

        @Override public T fr() { return rt.vl; }

        @Override
        public T pf() {
            sz--;
            var a = rt.vl;
            rt = rt.nx;
            ls = rt;
            return a;
        }

        @Override public boolean em() { return sz > 0; }

        @Override public void cl() { rt = null; }

        @Override public Iterator<T> iterator() { return this; }

        @Override public boolean hasNext() { return ls != null; }

        @Override
        public T next() {
            var a = ls.vl;
            ls = ls.nx;
            return a;
        }
    }
}
