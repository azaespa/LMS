package xaltius.azanespaul.LMS.books.exceptions;

public class BooksAlreadyBorrowedException extends RuntimeException{
    public BooksAlreadyBorrowedException(String id) {
        super("The book with id " + id + " is already borrowed by another user :(");
    }
}
