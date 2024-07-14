package xaltius.azanespaul.LMS.books.exceptions;

public class BooksUpdateErrorException extends RuntimeException{
    public BooksUpdateErrorException() {
        super("borrowed and borrowedBy can only be true and not null, or false and null at the same time");
    }
}
