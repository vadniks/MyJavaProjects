import java.util.*;

public class Task6 {

    public static void main(String[] args) {
        new One();
    }

    static class Student implements Comparable<Student> {
        int a;

        Student(int a) {
            this.a = a;
        }

        @Override
        public int compareTo(Student b) {
            return Integer.compare(b.a, a);
        }

        @Override
        public String toString() {
            return "Student{" +
                    "a= " + a +
                    " }";
        }
    }

    static class One {

        One() {
            Arr arr = new Arr();
            arr.add(new Student(0));
            arr.add(new Student(4));
            arr.add(new Student(5));
            arr.add(new Student(9));
            arr.add(new Student(8));
            arr.add(new Student(6));
            arr.add(new Student(7));
            arr.add(new Student(5));
            arr.add(new Student(0));
            arr.add(new Student(1));

            Arr arr2 = new Arr();
            arr2.add(new Student(10));
            arr2.add(new Student(12));
            arr2.add(new Student(11));
            arr2.add(new Student(15));
            arr2.add(new Student(16));
            arr2.add(new Student(16));

            Collections.sort(arr);
            //System.out.println(arr);

            System.out.println(combine(arr, arr2));

            int[] iDNumber = { 5, 6, 1, 0, 4, 9, 8, 7, 3, 2 };
            qsrt(iDNumber, 0, iDNumber.length - 1);
            //System.out.println(Arrays.toString(iDNumber));
        }

        void qsrt(int[] arr, int l, int h) {
            if (l >= h)
                return;

            int prtndx = prt(arr, l, h);

            qsrt(arr, l, prtndx -1);
            qsrt(arr, prtndx + 1, h);
        }

        int prt(int[] arr, int l, int h) {
            int pvt = arr[h];
            int i = l - 1;

            for (int j = l; j < h; j++) {
                if (arr[j] < pvt) {
                    i++;
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
            int t = arr[i + 1];
            arr[i + 1] = arr[h];
            arr[h] = t;

            return i + 1;
        }
    }

    static class SSBGPA implements Comparator<Student> {


        @Override
        public int compare(Student a, Student b) {
            return Integer.compare(a.a, b.a);
        }
    }

    static class Arr extends ArrayList<Student> {

        Arr() {
            super();
        }

        @Override
        public void sort(Comparator<? super Student> c) {
            //Arr a = (Arr) this.clone();
            insertionSort(this);
//            ListIterator<Student> i = this.listIterator();
//            Arr var4 = a;
//            int var5 = a.getSize();
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                Student e = var4.get(var6);
//                i.next();
//                i.set(e);
//            }
        }

        public static void insertionSort(Arr arr) {
            for(int i = 1; i < arr.size(); i++){
                Student currElem = arr.get(i);
                int prevKey = i - 1;
                while(prevKey >= 0 && arr.get(prevKey).a < currElem.a){
                    arr.set(prevKey+1, arr.get(prevKey));
                    prevKey--;
                }
                arr.set(prevKey+1, currElem);
            }
        }
    }

    static Arr combine(Arr a, Arr b) {
        Arr c = new Arr();
        c.addAll(a);
        c.addAll(b);
        Collections.sort(c);
        return c;
    }
}
