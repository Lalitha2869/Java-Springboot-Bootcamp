package Day_4;

import Day_4.Capstone.Book;

import java.util.ArrayList;
import java.util.List;

public class Task1BookList {
    public static void main(String[] args){
        List<Book> books=new ArrayList<>();
        books.add(new Book(101, "Clean Code", "Robert Martin", 500));
        books.add(new Book(102, "Effective Java", "Joshua Bloch", 750));
        books.add(new Book(103, "Java Basics", "James Gosling", 400));
        books.add(new Book(104, "Head First Java", "Kathy Sierra", 650));
        books.add(new Book(105, "Spring in Action", "Craig Walls", 800));
        System.out.println("Bookes sorted by Title");
        books.stream().map(Book::getTitle).sorted().forEach(System.out::println);



    }
    static class Book{
        private int id;
        private String title;
        private String author;
        private double price;

        public Book(int id,String title,String author,double price){
            this.id=id;
            this.title=title;
            this.author=author;
            this.price=price;

        }
        public String getTitle(){
            return title;
        }
    }

}
