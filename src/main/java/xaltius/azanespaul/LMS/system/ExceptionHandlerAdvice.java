package xaltius.azanespaul.LMS.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xaltius.azanespaul.LMS.books.exceptions.BooksAlreadyBorrowedException;
import xaltius.azanespaul.LMS.books.exceptions.BooksNotFoundException;
import xaltius.azanespaul.LMS.books.exceptions.BooksUpdateErrorException;
import xaltius.azanespaul.LMS.users.exceptions.UsersNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BooksNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleBooksNotFoundException(BooksNotFoundException ex) {
        return new Result(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(UsersNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleUsersNotFoundException(UsersNotFoundException ex) {
        return new Result(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(BooksAlreadyBorrowedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleBooksAlreadyBorrowedException(BooksAlreadyBorrowedException ex) {
        return new Result(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(BooksUpdateErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleBooksUpdateErrorException (BooksUpdateErrorException ex) {
        return new Result(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }
}
