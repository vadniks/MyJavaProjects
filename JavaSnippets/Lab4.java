
public class Lab4 {

    interface Nameable {
        String getName();
    }

    interface Priceable {
        int getPrice();
    }

    static class A implements Nameable, Priceable {

        @Override
        public String getName() {
            return "b.b.A";
        }

        @Override
        public int getPrice() {
            return 10;
        }
    }

    static class B implements Nameable, Priceable {

        @Override
        public String getName() {
            return "B";
        }

        @Override
        public int getPrice() {
            return 20;
        }
    }

    public static void main(String[] args) {
        Nameable a = new A();
        Nameable b = new B();
        System.out.println(a.getName());
        System.out.println(b.getName());

        System.out.println(((Priceable) a).getPrice());
        System.out.println(((Priceable) b).getPrice());
    }
}
