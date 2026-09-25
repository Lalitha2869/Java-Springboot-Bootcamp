package Day_4.Capstone;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // Add book
    public void addBook(Book book) {

        if (book.getPrice() < 0) {
            throw new IllegalArgumentException(
                    "Book price cannot be negative"
            );
        }

        if (repository.findById(book.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "Book ID already exists"
            );
        }

        repository.add(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    // Find book
    public Book getBookById(int id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Book not found with ID: " + id
                        )
                );
    }

    // Update book
    public void updateBook(
            int id,
            String title,
            String author,
            double price) {

        if (price < 0) {
            throw new IllegalArgumentException(
                    "Book price cannot be negative"
            );
        }

        Book book = getBookById(id);

        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
    }

    // Delete book
    public void deleteBook(int id) {

        if (!repository.deleteById(id)) {
            throw new IllegalArgumentException(
                    "Book not found with ID: " + id
            );
        }
    }

    // Search books
    public List<Book> searchBooks(String keyword) {

        return repository.findAll()
                .stream()
                .filter(book ->
                        book.getTitle()
                                .toLowerCase()
                                .contains(keyword.toLowerCase())
                                ||
                                book.getAuthor()
                                        .toLowerCase()
                                        .contains(keyword.toLowerCase())
                )
                .toList();
    }
}
