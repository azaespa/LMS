package xaltius.azanespaul.LMS.users.exceptions;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(String id) {
        super("Could not find a user with id " + id + " :(");
    }
}
