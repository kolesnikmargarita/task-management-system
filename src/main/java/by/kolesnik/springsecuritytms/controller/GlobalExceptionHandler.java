package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.exception.DeadlineInPastException;
import by.kolesnik.springsecuritytms.exception.NotCurrentUserTaskException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        final ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NotCurrentUserTaskException.class)
    public ResponseEntity<ErrorResponse> handleNotCurrentUserTaskException(NotCurrentUserTaskException exception) {
        final ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DeadlineInPastException.class)
    public ResponseEntity<ErrorResponse> handleDeadlineInPastException(DeadlineInPastException exception) {
        final ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        final ErrorResponse response = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
