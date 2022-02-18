
public class Lab2 {

    static class Circle {
        private int x, y, r;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }
    }

    static class CircleTest {

        public static void main(String[] args) {
            Circle c = new Circle();
            c.setX(10);
            c.setY(10);
            c.setR(10);

            System.out.println(c.getX() + ' ' + c.getY() + ' ' + c.getR());
        }
    }

    static class Human {
        private Head a;
        private Body b;
        private Hand[] c;
        private Leg[] d;

        public Head getA() {
            return a;
        }

        public void setA(Head a) {
            this.a = a;
        }

        public Body getB() {
            return b;
        }

        public void setB(Body b) {
            this.b = b;
        }

        public Hand[] getC() {
            return c;
        }

        public void setC(Hand[] c) {
            this.c = c;
        }

        public Leg[] getD() {
            return d;
        }

        public void setD(Leg[] d) {
            this.d = d;
        }

        public static void main(String[] args) {
            Human a = new Human();
            a.setA(new Head());
            a.setB(new Body());
            a.setC(new Hand[]{ new Hand(), new Hand() });
            a.setD(new Leg[]{ new Leg(), new Leg() });

            System.out.println("" +
                    (a.getA() == null) + ' ' +
                    (a.getB() == null) + ' ' +
                    (a.getC() == null) + ' ' +
                    (a.getD() == null) + ' '
            );
        }

        static class Head {
            private Object a;

            public Object getA() {
                return a;
            }

            public void setA(Object a) {
                this.a = a;
            }
        }

        static class Body {
            private Object a;

            public Object getA() {
                return a;
            }

            public void setA(Object a) {
                this.a = a;
            }
        }

        static class Hand {
            private Object a;

            public Object getA() {
                return a;
            }

            public void setA(Object a) {
                this.a = a;
            }
        }

        static class Leg {
            private Object a;

            public Object getA() {
                return a;
            }

            public void setA(Object a) {
                this.a = a;
            }
        }
    }

    static class Book {
        private String author;
        private String name;
        private int year;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    static class BookTest {

        public static void main(String[] args) {
            Book a = new Book();
            a.setAuthor("etfrghkjl");
            a.setName("tryghjkl");
            a.setYear(2000);

            System.out.println("" + a.getAuthor() + ' ' + a.getName() + ' ' + a.getYear());
        }
    }
}
