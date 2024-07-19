package xaltius.azanespaul.LMS.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xaltius.azanespaul.LMS.books.Books;

@Getter
public class Result {
    private String message;
    private int statusCode;
    private Books books;

    public Result(int statusCode, String message){
        this.message = message;
        this.statusCode = statusCode;
    }
    public Result(int statusCode, String message, Books books){
        this.statusCode = statusCode;
        this.message = message;
        this.books = books;
    }


}
