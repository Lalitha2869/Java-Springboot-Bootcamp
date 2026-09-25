package Day_4.Capstone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    // Add book
    public void add(Book book) {
        books.add(book);
    }

    // Get all books
    public List<Book> findAll() {
        return books;
    }

    // Find book by ID
    public Optional<Book> findById(int id) {

        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    // Delete book
    public boolean deleteById(int id) {

        return books.removeIf(book -> book.getId() == id);
    }
}
