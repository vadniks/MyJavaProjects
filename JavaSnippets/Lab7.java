import java.util.*;
import java.util.function.Function;

public class Lab7 {

    public static void main(String[] args) {
        t((Void a) -> new ArrayList<>(),  (Integer a) -> a);
        t((Void a) -> new LinkedList<>(), (Integer a) -> "Item_" + a);
        t((Void a) -> new A<>(),          (Integer a) -> a % 2 == 0);
    }

    static <T, L extends Collection<T>>
    void t(Function<Void, L> a, Function<Integer, T> b) {
        L c = a.apply(null);
        for (int i = 0; i < 10; i++)
            c.add(b.apply(i));
        for (var i : c)
            System.out.println(i);
    }

    static class A<T> implements Collection<T> {
        private Object[] arr;
        private int lst = -1;

        public A()
        { arr = new Object[0]; }

        @Override
        public int size()
        { return arr.length; }

        @Override
        public boolean isEmpty()
        { return size() == 0; }

        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < size(); i++)
                if (arr[i].equals(o))
                    return true;
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                @Override
                public boolean hasNext()
                { return lst + 1 < arr.length &&
                        arr[lst + 1] != null; }

                @Override
                public T next()
                { return (T) arr[++lst]; }
            };
        }

        @Override
        public Object[] toArray()
        { return arr.clone(); }

        @Override
        public <T1> T1[] toArray(T1[] t1s)
        { throw new UnsupportedOperationException(); }

        @Override
        public boolean add(T t) {
            var ar = new Object[arr.length + 1];
            System.arraycopy(arr, 0, ar, 0, arr.length);

            arr = ar;
            arr[arr.length - 1] = t;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            for (int i = 0; i < size(); i++)
                if (arr[i].equals(o)) {
                    arr[i] = null;
                    return true;
                }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            if (arr.length < collection.size())
                return false;

            for (var o : arr) {
                boolean a = false;
                for (var j : collection)
                    a = o.equals(j);
                if (!a)
                    return false;
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends T> collection) {
            for (var i : collection)
                add(i);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean a = true;
            for (var i : collection)
                a = remove(i);
            clear();
            return a;
        }

        @Override
        public boolean retainAll(Collection<?> collection)
        { throw new UnsupportedOperationException(); }

        @Override
        public void clear()
        { arr = new Object[1]; }
    }
}
