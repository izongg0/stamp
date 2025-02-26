package odin.stamp.common.exception;

public class InvalidException extends RuntimeException{

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
