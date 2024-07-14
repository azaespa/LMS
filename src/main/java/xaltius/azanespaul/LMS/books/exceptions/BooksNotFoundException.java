package xaltius.azanespaul.LMS.books.exceptions;

public class BooksNotFoundException extends RuntimeException {
    public BooksNotFoundException(String id) {
        super("Could not find a book with id " + id + " :(");
    }
}
