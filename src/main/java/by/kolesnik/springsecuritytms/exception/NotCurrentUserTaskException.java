package by.kolesnik.springsecuritytms.exception;

public class NotCurrentUserTaskException extends RuntimeException {

    public NotCurrentUserTaskException (String message) {
        super(message);
    }
}
