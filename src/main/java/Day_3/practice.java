package Day_3;
//
//class Student {
//
//    String name;
//    int age;
//
//    void study() {
//        System.out.println(name + " is studying");
//    }
//
//    void displayDetails() {
//        System.out.println("Name: " + name);
//        System.out.println("Age: " + age);
//    }
//}
//public class practice{
//
//    public static void main(String[] args) {
//
//        Student student1 = new Student(); //object
//
//        student1.name = "Lalitha";
//        student1.age = 22;
//
//        student1.displayDetails();
//        student1.study();
//    }
//}
//What is a Constructor?
//
//A constructor is a special block of code used to initialize an object.
//Java Program Using Constructor
//

//Getters and Setters

//get--> read data
//set-->modify the data.
//For encapsulation, commonly:
//private String name;
//and:
//public String getName()
//=======
//example code:
//class Employee{
//    private String name;
//    private double Salary;
//    public String getName(){
//        return name;
//
//    }
//    public void setName(String name){
//        this.name=name;
//    }
//    public double getSalary(){
//        return Salary;
//
//    }
//    public void setSalary(double Salary){
//        this.Salary=Salary;
//    }
//}
//public class practice {
//    public static void main(String[] args){
//
//        Employee emp2=new Employee();
//        emp2.setName("karthik");
//        emp2.setSalary(670000);
//        System.out.println(emp2.getName());
//        System.out.println(emp2.getSalary());
//    }
//}

//Inheritance means:
//
//One class can inherit properties and behavior from another class.
//
//class Animal{
//    void eat(){
//        System.out.println(("animal is eating"));
//    }
//    void sleep(){
//        System.out.println("animal is sleeping");
//    }
//}
////java syntax
//class Dog extends Animal{
//    void bark(){
//        System.out.println("dog is barking");
//    }
//}
//public class practice {
//    public static void main(String[] args){
//        Dog d=new Dog();
//        d.eat();
//        d.bark();
//        d.sleep();
//    }
//}
//Inheritance Example with Employee
//
//class Employee{
//    String name;
//    double salary;
//
//    void work(){
//        System.out.println(name+" is working");
//    }
//
//}
//class Developer extends Employee{
//    void writecode(){
//        System.out.println(name+" writes the code");
//    }
//}
//public class practice {
//    public static void main(String[] args){
//        Developer dev = new Developer();
//        dev.name = "Lalitha";
//        dev.salary = 50000;
//        dev.writecode();
//        dev.work();
//
//    }
//}

//Method Overriding
//The child overrides the parent's method.
//

//polymorphism:: one thing can takes many forms
//class Animal {
//
//    void sound() {
//        System.out.println("Animal sound");
//    }
//}
//
//class Dog extends Animal {
//
//    @Override
//    void sound() {
//        System.out.println("Dog barks");
//    }
//}
//
//class Cat extends Animal {
//
//    @Override
//    void sound() {
//        System.out.println("Cat meows");
//    }
//}
//public class practice {
//    public static void main(String[] args){
//        Animal animal;
//
//        animal = new Dog();
//        animal.sound();
//
//        animal = new Cat();
//        animal.sound();
//    }
//}

//Abstract Class Example

//abstract class Animal{
//    abstract void sound();
//    void  eat(){
//        System.out.println("animal is eating");
//    }
//}
//class Dog extends Animal {
//
//    @Override
//    void sound() {
//        System.out.println("Dog barks");
//    }
//}
//class cat extends Animal{
//    @Override
//    void sound(){
//        System.out.println("cat mewos");
//    }
//}
//public class practice {
//    public static void main(String[] args){
//        Dog d=new Dog();
//        d.sound();
//        d.eat();
//    }
//}
//Can we create an object of an abstract class?
//
//No.
//
//This is invalid:
//
//Animal animal = new Animal();
//
//Because Animal is abstract.
//
//But this is valid:
//
//Animal animal = new Dog();
//
//That's actually a powerful combination:
//
//abstract parent reference
//        ↓
//Dog object

//. Interface Example
interface Notification {

    void send();
}
class EmailNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Sending email");
    }
}
class SmsNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Sending SMS");
    }
}

public class practice {
    public static void main(String[] args){
        Notification notification;

        notification = new EmailNotification();
        notification.send();

        notification = new SmsNotification();
        notification.send();
    }
}
