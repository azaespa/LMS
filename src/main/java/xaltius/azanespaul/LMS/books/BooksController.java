package xaltius.azanespaul.LMS.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xaltius.azanespaul.LMS.system.Result;

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
    public Result getBooksById(@PathVariable Long id) {
        Books books = booksService.findBooksById(id);
        return new Result(HttpStatus.OK.value(), "Find One Success", books);
    }

    @PutMapping("/api/books/{id}")
    public Result updateBooksById(@RequestBody Books books, @PathVariable Long id) {
        Books updatedBooks = booksService.updateBooksById(books, id);
        return new Result(HttpStatus.OK.value(), "Update One Success", updatedBooks);
    }

    @DeleteMapping("/api/books/{id}")
    public Result deleteBooksById(@PathVariable Long id) {
        booksService.deleteBooksById(id);
        return new Result(HttpStatus.OK.value(), "Delete One Success");
    }

    @PostMapping("/api/books/{bookId}/borrow/{userId}")
    public Result borrowBooksById(@PathVariable Long bookId, @PathVariable Long userId) {
        Books borrowedBooks = booksService.borrowBooksById(bookId, userId);
        return new Result(HttpStatus.OK.value(), "Borrow One Success", borrowedBooks);
    }

    @PostMapping("/api/books/{bookId}/return")
    public Books returnBooksById(@PathVariable Long bookId) {
        return booksService.returnBooksById(bookId);
    }
}
