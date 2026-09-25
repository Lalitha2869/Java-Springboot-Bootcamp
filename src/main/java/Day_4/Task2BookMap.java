package Day_4;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Task2BookMap {

    public static void main(String[] args) {

        Map<Integer, Book> books = new HashMap<>();

        books.put(
                101,
                new Book(101, "Clean Code", "Robert Martin", 500)
        );

        books.put(
                102,
                new Book(102, "Effective Java", "Joshua Bloch", 750)
        );

        books.put(
                103,
                new Book(103, "Java Basics", "James Gosling", 400)
        );

        // Existing book
        Optional<Book> book = findBookById(books, 102);

        if (book.isPresent()) {
            System.out.println("Book found:");
            System.out.println(book.get());
        }

        // Missing book
        Optional<Book> missingBook = findBookById(books, 999);

        if (missingBook.isEmpty()) {
            System.out.println("Book with ID 999 not found.");
        }
    }


    static Optional<Book> findBookById(
            Map<Integer, Book> books,
            int id) {

        return Optional.ofNullable(
                books.get(id)
        );
    }


    static class Book {

        private int id;
        private String title;
        private String author;
        private double price;

        public Book(
                int id,
                String title,
                String author,
                double price) {

            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}