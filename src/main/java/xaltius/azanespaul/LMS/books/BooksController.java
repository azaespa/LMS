package xaltius.azanespaul.LMS.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xaltius.azanespaul.LMS.books.converter.BooksToBooksDtoConverter;
import xaltius.azanespaul.LMS.books.dto.BooksDto;
import xaltius.azanespaul.LMS.system.Result;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BooksController {

    @Autowired
    private BooksService booksService;

    private final BooksToBooksDtoConverter booksToBooksDtoConverter;

    public BooksController(BooksToBooksDtoConverter booksToBooksDtoConverter) {
        this.booksToBooksDtoConverter = booksToBooksDtoConverter;
    }

    @PostMapping("/api/books")
    public Result saveBooks(@RequestBody Books books) {
        Books savedBooks = booksService.saveBooks(books);
        BooksDto booksDto = this.booksToBooksDtoConverter.convert(savedBooks);

        return new Result("Save One Success", HttpStatus.OK.value(), booksDto);
    }

    @GetMapping("/api/books")
    public Result getAllBooks() {
        List<Books> booksList = booksService.findAllBooks();
        List<BooksDto> booksDtos = booksList.stream().map(this.booksToBooksDtoConverter::convert).toList();

        return new Result("Find All Success", HttpStatus.OK.value(), booksDtos) ;
    }

    @GetMapping("/api/books/{id}")
    public Result getBooksById(@PathVariable Long id) {
        Books books = booksService.findBooksById(id);
        BooksDto booksDto = this.booksToBooksDtoConverter.convert(books);

        return new Result("Find One Success", HttpStatus.OK.value(), booksDto);
    }

    @PutMapping("/api/books/{id}")
    public Result updateBooksById(@RequestBody Books books, @PathVariable Long id) {
        Books updatedBooks = booksService.updateBooksById(books, id);
        BooksDto booksDto = this.booksToBooksDtoConverter.convert(updatedBooks);

        return new Result("Update One Success", HttpStatus.OK.value(), booksDto);
    }

    @DeleteMapping("/api/books/{id}")
    public Result deleteBooksById(@PathVariable Long id) {
        booksService.deleteBooksById(id);
        return new Result("Delete One Success", HttpStatus.OK.value(), null);
    }

    @PostMapping("/api/books/{bookId}/borrow/{userId}")
    public Result borrowBooksById(@PathVariable Long bookId, @PathVariable Long userId) {
        Books borrowedBooks = booksService.borrowBooksById(bookId, userId);
        BooksDto booksDto = this.booksToBooksDtoConverter.convert(borrowedBooks);

        return new Result("Borrow One Success", HttpStatus.OK.value(), booksDto);
    }

    @PostMapping("/api/books/{bookId}/return")
    public Result returnBooksById(@PathVariable Long bookId) {
        Books returnedBooks = booksService.returnBooksById(bookId);
        BooksDto booksDto = this.booksToBooksDtoConverter.convert(returnedBooks);

        return new Result("Returned One Success", HttpStatus.OK.value(), booksDto);
    }
}
