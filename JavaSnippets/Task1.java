
public class Task1 {

    public static class Dog {
        private String name;
        private int age;

        public Dog(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public int intoHumane() {
            return age + 7;
        }
    }

    public static class DogDriver {

        public static void main(String[] args) {
            Dog d = new Dog("Mike", 1);
            d.setAge(2);
            System.out.println("Dog's age: " + d.getAge());
            System.out.println("Dog's age as human: " + d.intoHumane());
        }
    }

    public static class Ball {
        private int weigh;

        public Ball(int weigh) {
            this.weigh = weigh;
        }

        public int getWeigh() {
            return weigh;
        }

        public void setWeigh(int weigh) {
            this.weigh = weigh;
        }

        @Override
        public String toString() {
            return "Ball{" +
                    "weigh=" + weigh +
                    '}';
        }
    }

    public static class Book {
        private String name;
        private String author;

        public Book(String name, String author) {
            this.name = name;
            this.author = author;
        }

        public String getName() {
            return name;
        }

        public String getAuthor() {
            return author;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    '}';
        }
    }
}
