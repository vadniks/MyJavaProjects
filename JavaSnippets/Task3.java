
public class Task3 {

    static abstract class Shape {
        protected String color;
        protected boolean filled;

        Shape(String color, boolean filled) {
            this.color = color;
            this.filled = filled;
        }

        Shape() {}

        public String getColor() {
            return color;
        }

        public boolean isFilled() {
            return filled;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setFilled(boolean filled) {
            this.filled = filled;
        }

        abstract double getArea();

        abstract double getPerimeter();

        @Override
        public String toString() {
            return "Shape{" +
                    "color='" + color + '\'' +
                    ", filled=" + filled +
                    '}';
        }
    }

    static class Circle extends Shape {
        private double radius;

        Circle() {}

        Circle(double radius) {
            this.radius = radius;
        }

        Circle(double radius, String color, boolean filled) {
            super(color, filled);
        }

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        @Override
        double getArea() {
            return Math.pow(Math.PI * radius, 2);
        }

        @Override
        double getPerimeter() {
            return 2 * Math.PI * radius;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "color='" + color + '\'' +
                    ", filled=" + filled +
                    ", radius=" + radius +
                    '}';
        }
    }

    static class Rectangle extends Shape {
        private double width;
        private double length;

        Rectangle() {}

        public Rectangle(double width, double length) {
            this.width = width;
            this.length = length;
        }

        public Rectangle(String color, boolean filled, double width, double length) {
            super(color, filled);
            this.width = width;
            this.length = length;
        }

        public double getWidth() {
            return width;
        }

        public double getLength() {
            return length;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setLength(double length) {
            this.length = length;
        }

        @Override
        double getArea() {
            return width * length;
        }

        @Override
        double getPerimeter() {
            return (width + length) * 2;
        }

        @Override
        public String toString() {
            return "Rectangle{" +
                    "color='" + color + '\'' +
                    ", filled=" + filled +
                    ", width=" + width +
                    ", length=" + length +
                    '}';
        }
    }

    static class Square extends Rectangle {

        Square() {}

        public Square(double side) {
            super(side, side);
        }

        public Square(String color, boolean filled, double side) {
            super(color, filled, side, side);
        }

        public double getSide() {
            return getWidth();
        }

        public void setSide(double side) {
            setWidth(side);
            setLength(side);
        }

        @Override
        public String toString() {
            return "Square{" +
                    "color='" + color + '\'' +
                    ", filled=" + filled +
                    ", width=" + getSide() +
                    ", length=" + getSide() +
                    '}';
        }

        @Override
        double getArea() {
            return getSide() * getSide();
        }

        @Override
        double getPerimeter() {
            return getSide() * 4;
        }
    }

    static class TestShape {

        public static void main(String[] args) {
            Shape s1 = new Circle(5.5, "RED", false); // Upcast Circle to Shape
            System.out.println(s1);                                     // which version?
            System.out.println(s1.getArea());                           // which version?
            System.out.println(s1.getPerimeter());                      // which version?
            System.out.println(s1.getColor());
            System.out.println(s1.isFilled());
            //System.out.println(s1.getRadius()); // s1 is defined as Shape which doesn't contain this method

            Circle c1 = (Circle)s1;                                     // Downcast back to Circle
            System.out.println(c1);
            System.out.println(c1.getArea());
            System.out.println(c1.getPerimeter());
            System.out.println(c1.getColor());
            System.out.println(c1.isFilled());
            System.out.println(c1.getRadius());

            //Shape s2 = new Shape(); // abstract classes cannot be instantiated

            Shape s3 = new Rectangle("RED", false, 1.0, 2.0); // Upcast
            System.out.println(s3);
            System.out.println(s3.getArea());
            System.out.println(s3.getPerimeter());
            System.out.println(s3.getColor());
            //System.out.println(s3.getLength()); // s1 is defined as Shape which doesn't contain this method

            Rectangle r1 = (Rectangle)s3; // Downcast
            System.out.println(r1);
            System.out.println(r1.getArea());
            System.out.println(r1.getColor());
            System.out.println(r1.getLength());

            Shape s4 = new Square(6.6); // Upcast
            System.out.println(s4);
            System.out.println(s4.getArea());
            System.out.println(s4.getColor());
            //System.out.println(s4.getSide()); // s1 is defined as Shape which doesn't contain this method

            // Take note that we downcast Shape s4 to Rectangle,
            // which is a superclass of Square, instead of Square
            Rectangle r2 = (Rectangle)s4;
            System.out.println(r2);
            System.out.println(r2.getArea());
            System.out.println(r2.getColor());
            //System.out.println(r2.getSide()); // s1 is defined as Rectangle which doesn't contain this method
            System.out.println(r2.getLength());

            // Downcast Rectangle r2 to Square
            Square sq1 = (Square)r2;
            System.out.println(sq1);
            System.out.println(sq1.getArea());
            System.out.println(sq1.getColor());
            System.out.println(sq1.getSide());
            System.out.println(sq1.getLength());
        }
    }

    interface Movable {
        void moveUp();
        void moveDown();
        void moveLeft();
        void moveRight();
    }

    static class MovablePoint implements Movable {
        protected int x;
        protected int y;
        protected int xSpeed;
        protected int ySpeed;

        public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
            this.x = x;
            this.y = y;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }

        @Override
        public void moveUp() {
            y++;
        }

        @Override
        public void moveDown() {
            y--;
        }

        @Override
        public void moveLeft() {
            x--;
        }

        @Override
        public void moveRight() {
            x++;
        }

        @Override
        public String toString() {
            return "MovablePoint{" +
                    "x=" + x +
                    ", y=" + y +
                    ", xSpeed=" + xSpeed +
                    ", ySpeed=" + ySpeed +
                    '}';
        }
    }

    static class MovableCircle extends MovablePoint {
        private int radius;
        private MovablePoint center;

        public MovableCircle(int x, int y, int xSpeed, int ySpeed, int radius, MovablePoint center) {
            super(x, y, xSpeed, ySpeed);
            this.radius = radius;
            this.center = center;
        }

        @Override
        public String toString() {
            return "MovableCircle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", xSpeed=" + xSpeed +
                    ", ySpeed=" + ySpeed +
                    ", center=" + center +
                    '}';
        }
    }

    static class MovableRectangle extends MovablePoint {
        private MovablePoint topLeft;
        private MovablePoint bottomRight;

        public MovableRectangle(int x, int y, int xSpeed, int ySpeed, MovablePoint topLeft) {
            super(x, y, xSpeed, ySpeed);
            this.topLeft = topLeft;
        }
    }
}
