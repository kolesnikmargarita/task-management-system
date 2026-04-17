package by.kolesnik.springsecuritytms.exception;

public class DeadlineInPastException extends RuntimeException {

    public DeadlineInPastException (String message) {
        super(message);
    }
}
