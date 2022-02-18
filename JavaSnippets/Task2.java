
public class Task2 {

    public static class Author {
        private String name;
        private String email;
        private char gender;

        public Author(String name, String email, char gender) {
            this.name = name;
            this.email = email;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public char getGender() {
            return gender;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", gender=" + gender +
                    '}';
        }
    }

    public static class TestAuthor {

        public static void main(String[] args) {
            Author a = new Author("Mike", "mike@a.com", 'm');
            a.setEmail("reg@fev.fvwe");
            System.out.println(a.toString());
        }
    }

    public static class Ball {
        private double x = 0.0d;
        private double y = 0.0d;

        public Ball(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Ball() {}

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setXY(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void move(double xDisp, double yDisp) {
            x += xDisp;
            y += yDisp;
        }

        public String toString() {
            return "Ball @ (" + x + ", " + y + ")";
        }
    }

    public static class TestBall {

        public static void main(String[] args) {
            Ball b = new Ball(0.0, 0.0);
            b.setXY(1.0, 1.0);
            b.move(0.5, 0.5);
            System.out.println(b.toString());
        }
    }
}
