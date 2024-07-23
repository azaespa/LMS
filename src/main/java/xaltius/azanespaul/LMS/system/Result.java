package xaltius.azanespaul.LMS.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xaltius.azanespaul.LMS.books.Books;
import xaltius.azanespaul.LMS.users.Users;

import java.util.List;

@Getter
public class Result {
    private String message;
    private int statusCode;
    private Books books;
    private List<Books> booksList;
    private Users users;
    private List<Users> usersList;

    public Result(int statusCode, String message){
        this.message = message;
        this.statusCode = statusCode;
    }

    public Result(int statusCode, String message, Books books){
        this.statusCode = statusCode;
        this.message = message;
        this.books = books;
    }

    public Result(int statusCode, String message, Users users){
        this.statusCode = statusCode;
        this.message = message;
        this.users = users;
    }

    public Result(int statusCode, String message, List<Users> usersList){
        this.statusCode = statusCode;
        this.message = message;
        this.usersList = usersList;
    }
}
