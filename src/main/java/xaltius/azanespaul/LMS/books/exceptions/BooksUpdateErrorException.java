package xaltius.azanespaul.LMS.books.exceptions;

public class BooksUpdateErrorException extends RuntimeException{
    public BooksUpdateErrorException() {
        super("You cannot update other properties except Title and Author");
    }
}
