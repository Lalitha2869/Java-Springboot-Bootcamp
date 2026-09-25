package Day_3;
public class StudentTeacher {

    // Abstract parent class
    static abstract class Person {

        private String name;
        private int age;

        // Constructor
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getters
        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        // Abstract method
        public abstract void introduce();
    }


    // Student subclass
    static class Student extends Person {

        public Student(String name, int age) {
            super(name, age);
        }

        @Override
        public void introduce() {
            System.out.println(
                    "I am " + getName()
                            + ", I am a student and I am "
                            + getAge() + " years old."
            );
        }
    }


    // Teacher subclass
    static class Teacher extends Person {

        public Teacher(String name, int age) {
            super(name, age);
        }

        @Override
        public void introduce() {
            System.out.println(
                    "I am " + getName()
                            + ", I am a teacher and I am "
                            + getAge() + " years old."
            );
        }
    }


    // Main method
    public static void main(String[] args) {

        // Person reference pointing to Student object
        Person person1 = new Student("Lalitha", 22);

        // Person reference pointing to Teacher object
        Person person2 = new Teacher("Ravi", 35);

        // Polymorphism
        person1.introduce();
        person2.introduce();
    }
}