import java.util.Arrays;

public class Lab3 {

    static abstract class Dish {

        abstract void wash();

        static void test() {
            Dish a = new Plate(5);
            Dish b = new Cup(4);
            a.wash();
            b.wash();
        }
    }

    static class Plate extends Dish {
        private int a;

        Plate(int a) {
            this.a = a;
        }

        @Override
        void wash() {
            System.out.println("plate is being washed " + a + " minutes");
        }
    }

    static class Cup extends Dish {
        private int a;

        Cup(int a) {
            this.a = a;
        }

        @Override
        void wash() {
            System.out.println("cup is being washed " + a + " minutes");
        }
    }

    static abstract class Dog {

        abstract void bark();

        static void test() {
            Dog a = new ADog();
            Dog b = new BDog();
            a.bark();
            b.bark();
        }
    }

    static class ADog extends Dog {

        @Override
        void bark() {
            System.out.println("b.b.A is barking");
        }
    }

    static class BDog extends Dog {

        @Override
        void bark() {
            System.out.println("B is barking");
        }
    }

    static abstract class Furniture {

        abstract int cost();

        @Override
        public String toString() {
            return "" + cost();
        }
    }

    static class Table extends Furniture {

        @Override
        int cost() {
            return 1000;
        }
    }

    static class Chair extends Furniture {

        @Override
        int cost() {
            return 500;
        }
    }


    static class FurnitureShop {
        private Furniture[] a;

        FurnitureShop(Furniture[] a) {
            this. a = a;
        }

        void prt() {
            System.out.println(Arrays.toString(a));
        }

        static void test() {
            new FurnitureShop(new Furniture[]{ new Table(), new Chair() }).prt();
        }
    }

    public static void main(String[] args) {
        Dish.test();
        Dog.test();
        FurnitureShop.test();
    }
}
