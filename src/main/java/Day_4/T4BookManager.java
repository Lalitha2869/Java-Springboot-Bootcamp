package Day_4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class T4BookManager {

    private static final Map<Integer, Book> books =
            new HashMap<>();

    private static final Scanner scanner =
            new Scanner(System.in);


    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            showMenu();

            try {

                int choice =
                        Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addBook();
                        break;

                    case 2:
                        listBooks();
                        break;

                    case 3:
                        deleteBook();
                        break;

                    case 4:
                        running = false;
                        System.out.println(
                                "Exiting Book Manager. Goodbye!"
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please select 1-4."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (BookNotFoundException e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }


    // Display menu
    private static void showMenu() {

        System.out.println();
        System.out.println("============================");
        System.out.println("       BOOK MANAGER");
        System.out.println("============================");
        System.out.println("1. Add Book");
        System.out.println("2. List Books");
        System.out.println("3. Delete Book");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }


    // Add book
    private static void addBook() {

        System.out.print("Enter Book ID: ");

        int id =
                Integer.parseInt(scanner.nextLine());

        if (books.containsKey(id)) {

            System.out.println(
                    "Book with this ID already exists."
            );

            return;
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price =
                Double.parseDouble(scanner.nextLine());

        Book book =
                new Book(
                        id,
                        title,
                        author,
                        price
                );

        books.put(id, book);

        System.out.println(
                "Book added successfully!"
        );
    }


    // List books
    private static void listBooks() {

        if (books.isEmpty()) {

            System.out.println(
                    "No books available."
            );

            return;
        }

        System.out.println();
        System.out.println("===== BOOKS =====");

        books.values()
                .stream()
                .sorted(
                        (book1, book2) ->
                                book1.getTitle()
                                        .compareToIgnoreCase(
                                                book2.getTitle()
                                        )
                )
                .forEach(System.out::println);
    }


    // Delete book
    private static void deleteBook() {

        System.out.print("Enter Book ID: ");

        int id =
                Integer.parseInt(scanner.nextLine());

        if (!books.containsKey(id)) {

            throw new BookNotFoundException(
                    "Book with ID " + id +
                            " does not exist."
            );
        }

        books.remove(id);

        System.out.println(
                "Book deleted successfully!"
        );
    }


    // Book class
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

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public double getPrice() {
            return price;
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


    // Custom exception
    static class BookNotFoundException
            extends RuntimeException {

        public BookNotFoundException(String message) {
            super(message);
        }
    }
}