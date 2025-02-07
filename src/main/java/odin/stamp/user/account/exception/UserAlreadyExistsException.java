package odin.stamp.user.account.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("alreadyExists.user");
    }
}
