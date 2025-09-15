package org.mss.song.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidId(InvalidIdException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "errorMessage", String.format("Invalid value '%s' for ID. Must be a positive integer", ex.getMessage()),
                        "errorCode", "400"
                ));
    }

    @ExceptionHandler(IdLengthException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidIdsLength(IdLengthException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "errorMessage", String.format("CSV string is too long: received %s characters, maximum allowed is 200", ex.getMessage()),
                        "errorCode", "400"
                ));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "errorMessage", String.format("Song with ID=%s not found", ex.getMessage()),
                "errorCode", "404"
        ));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "errorMessage", "An error occurred on the server",
                "errorCode", "500"
        ));
    }

    @ExceptionHandler(PersistenceConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(PersistenceConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "errorMessage", String.format("Metadata for resource ID=%s already exists", ex.getMessage()),
                "errorCode", "409"
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage())
        );
        ErrorResponse response = new ErrorResponse(
                "Validation error",
                details,
                "400"
        );
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}