package Day_4.CustomException;

import java.util.HashMap;
import java.util.Map;

public class Task3CustomException {

    public static void main(String[] args) {

        Map<Integer, Book> books = new HashMap<>();

        books.put(
                101,
                new Book(
                        101,
                        "Clean Code",
                        "Robert Martin",
                        500
                )
        );

        try {

            Book book = findBookById(books, 999);

            System.out.println(book);

        } catch (BookNotFoundException e) {

            System.out.println(
                    "Friendly Error: " + e.getMessage()
            );
        }
    }


    static Book findBookById(
            Map<Integer, Book> books,
            int id) {

        Book book = books.get(id);

        if (book == null) {

            throw new BookNotFoundException(
                    "Book with ID " + id + " was not found."
            );
        }

        return book;
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
