import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Task8 {

    interface IWaitList<E> {
        void add(E a);
        E remove();
        boolean contains(E a);
        boolean containsAll(Collection<E> a);
        boolean isEmpty();
    }

    static class WaitList<E> implements IWaitList<E> {
        protected ConcurrentLinkedQueue<E> content;

        WaitList() { content = new ConcurrentLinkedQueue<>(); }

        public WaitList(ConcurrentLinkedQueue<E> content)
        { this.content = content; }

        @Override
        public void add(E a)
        { content.add(a); }

        @Override
        public E remove()
        { return content.remove(); }

        @Override
        public boolean contains(E a)
        { return content.contains(a); }

        @Override
        public boolean containsAll(Collection<E> a)
        { return content.containsAll(a); }

        @Override
        public boolean isEmpty()
        { return content.isEmpty(); }

        @Override
        public String toString()
        { return "WaitList: " + content.toString(); }
    }

    static class BoundedWaitList<E> extends WaitList<E> {
        private final int capacity;

        public BoundedWaitList(int capacity) {
            super(new ConcurrentLinkedQueue<>());
            this.capacity = capacity;
        }

        public int getCapacity()
        { return capacity; }

        @Override
        public void add(E a) {
            if (content.size() + 1 <= capacity) super.add(a);
            else throw new ArrayIndexOutOfBoundsException();
        }

        @Override
        public String toString()
        { return "BoundedWaitList: " + content.toString(); }
    }

    static class UnfairWaitList<E> extends WaitList<E> {
        private ArrayList<E> dltd;

        public UnfairWaitList()
        { dltd = new ArrayList<>(); }

        @Override
        public void add(E a) {
            for (var b : dltd)
                if (b.equals(a))
                    throw new IllegalArgumentException();
            super.add(a);
        }

        void remove(E a) {
            for (var b : content)
                if (b.equals(a))
                    throw new IllegalArgumentException();
                else break;

            dltd.add(a);
            content.remove(a);
        }

        void moveToBack(E a) {
            content.remove(a);
            content.add(a);
        }
    }

    public static void main(String[] args) {
        var a = new BoundedWaitList<>(1);
        a.add(0);
        try { a.add(1); }
        catch (Exception e) { System.out.println("#####"); }

        var b = new UnfairWaitList<>();
        b.add(0);
        b.add(1);
        b.add(2);

        b.remove(1);
        try { b.add(1); }
        catch (Exception e) { System.out.println("#####"); }

        b.moveToBack(0);
        System.out.println(b);
    }
}
