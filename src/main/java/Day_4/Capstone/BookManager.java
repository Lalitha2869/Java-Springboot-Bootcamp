package Day_4.Capstone;

import java.util.List;
import java.util.Scanner;

public class BookManager {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BookRepository repository = new BookRepository();

        BookService service = new BookService(repository);

        addSampleBooks(service);

        boolean running = true;

        while (running) {

            displayMenu();

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addBook(service);
                        break;

                    case 2:
                        viewBooks(service);
                        break;

                    case 3:
                        findBook(service);
                        break;

                    case 4:
                        updateBook(service);
                        break;

                    case 5:
                        deleteBook(service);
                        break;

                    case 6:
                        searchBooks(service);
                        break;

                    case 7:
                        running = false;
                        System.out.println(
                                "Thank you for using Book Manager!"
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please select 1-7."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }


    // Display menu
    private static void displayMenu() {

        System.out.println();
        System.out.println("=============================");
        System.out.println("        BOOK MANAGER");
        System.out.println("=============================");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Find Book");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
        System.out.println("6. Search Books");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }


    // Add book
    private static void addBook(BookService service) {

        System.out.print("Enter Book ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Book book = new Book(
                id,
                title,
                author,
                price
        );

        service.addBook(book);

        System.out.println("Book added successfully!");
    }


    // View books
    private static void viewBooks(BookService service) {

        List<Book> books = service.getAllBooks();

        if (books.isEmpty()) {

            System.out.println("No books available.");

            return;
        }

        System.out.println();
        System.out.println("===== ALL BOOKS =====");

        books.forEach(System.out::println);
    }


    // Find book
    private static void findBook(BookService service) {

        System.out.print("Enter Book ID: ");

        int id = Integer.parseInt(scanner.nextLine());

        Book book = service.getBookById(id);

        System.out.println("Book found:");
        System.out.println(book);
    }


    // Update book
    private static void updateBook(BookService service) {

        System.out.print("Enter Book ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter New Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter New Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter New Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        service.updateBook(
                id,
                title,
                author,
                price
        );

        System.out.println(
                "Book updated successfully!"
        );
    }


    // Delete book
    private static void deleteBook(BookService service) {

        System.out.print("Enter Book ID: ");

        int id = Integer.parseInt(scanner.nextLine());

        service.deleteBook(id);

        System.out.println(
                "Book deleted successfully!"
        );
    }


    // Search books
    private static void searchBooks(BookService service) {

        System.out.print("Enter title or author keyword: ");

        String keyword = scanner.nextLine();

        List<Book> results =
                service.searchBooks(keyword);

        if (results.isEmpty()) {

            System.out.println(
                    "No matching books found."
            );

            return;
        }

        System.out.println();
        System.out.println("===== SEARCH RESULTS =====");

        results.forEach(System.out::println);
    }


    // Sample data
    private static void addSampleBooks(
            BookService service) {

        service.addBook(
                new Book(
                        101,
                        "Clean Code",
                        "Robert C. Martin",
                        500
                )
        );

        service.addBook(
                new Book(
                        102,
                        "Effective Java",
                        "Joshua Bloch",
                        750
                )
        );

        service.addBook(
                new Book(
                        103,
                        "Head First Java",
                        "Kathy Sierra",
                        650
                )
        );
    }
}
