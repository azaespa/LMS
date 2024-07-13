package xaltius.azanespaul.LMS.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {

    @Autowired
    private BooksService booksService;

    @PostMapping("/api/books")
    public Books saveBooks(@RequestBody Books books) {
        return booksService.saveBooks(books);
    }

    @GetMapping("/api/books")
    public List<Books> getAllBooks() {
        return booksService.findAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public Books getBooksById(@PathVariable Long id) {
        return booksService.findBooksById(id);
    }

    @PutMapping("/api/books/{id}")
    public Books updateBooksById(@RequestBody Books books, @PathVariable Long id) {
        return booksService.updateBooksById(books, id);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBooksById(@PathVariable Long id) {
        booksService.deleteBooksById(id);
    }

    @PostMapping("/api/books/{bookId}/borrow/{userId}")
    public Books borrowBooksById(@PathVariable Long bookId, @PathVariable Long userId) {
        return booksService.borrowBooksById(bookId, userId);
    }

    @PostMapping("/api/books/{bookId}/return")
    public Books returnBooksById(@PathVariable Long bookId) {
        return booksService.returnBooksById(bookId);
    }
}
